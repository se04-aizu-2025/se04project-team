package dotnet.sort.presentation.feature.quiz

import androidx.lifecycle.viewModelScope
import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.quiz.model.QuizFeedback
import dotnet.sort.domain.quiz.usecase.GenerateQuizQuestionUseCase
import dotnet.sort.domain.quiz.usecase.CalculateQuizScoreUseCase
import dotnet.sort.presentation.common.viewmodel.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

/**
 * Quiz画面のViewModel。
 *
 * クイズ機能の状態管理とビジネスロジックを担当します。
 */
@Factory
class QuizViewModel(
    private val generateQuizQuestionUseCase: GenerateQuizQuestionUseCase,
    private val validateQuizAnswerUseCase: dotnet.sort.domain.quiz.usecase.ValidateQuizAnswerUseCase,
    private val calculateQuizScoreUseCase: dotnet.sort.domain.quiz.usecase.CalculateQuizScoreUseCase
) : BaseViewModel<QuizState, QuizIntent>(QuizState()) {
    
    private var timerJob: Job? = null
    
    override fun send(intent: QuizIntent) {
        when (intent) {
            is QuizIntent.StartGame -> startGame()
            is QuizIntent.SubmitAnswer -> submitAnswer(intent.selectedIndices)
            is QuizIntent.NextQuestion -> nextQuestion()
            is QuizIntent.Tick -> tick()
            is QuizIntent.EndGame -> endGame()
        }
    }
    
    private fun startGame() {
        updateState {
            copy(
                isGameActive = true,
                score = 0,
                feedback = null
            )
        }
        nextQuestion()
    }
    
    private fun nextQuestion() {
        // ランダムにアルゴリズムを選択
        val randomAlgorithm = SortType.entries.random()
        
        execute {
            val question = generateQuizQuestionUseCase(randomAlgorithm)
            updateState {
                copy(
                    currentQuestion = question,
                    timeLeftSeconds = question.timeLimitSeconds,
                    feedback = null
                )
            }
            startTimer()
        }
    }
    
    private fun submitAnswer(selectedIndices: Pair<Int, Int>) {
        val currentQuestion = state.value.currentQuestion ?: return
        
        val feedback = validateQuizAnswerUseCase(currentQuestion.correctIndices, selectedIndices)
        
        stopTimer()
        
        if (feedback is dotnet.sort.domain.quiz.model.QuizFeedback.Correct) {
            val scoreDelta = calculateQuizScoreUseCase(
                isCorrect = true,
                timeLeftSeconds = state.value.timeLeftSeconds,
                currentCombo = state.value.consecutiveCorrectCount
            )
            
            updateState {
                copy(
                    score = score + scoreDelta,
                    consecutiveCorrectCount = consecutiveCorrectCount + 1,
                    feedback = dotnet.sort.domain.quiz.model.QuizFeedback.Correct(scoreDelta)
                )
            }
        } else {
            updateState {
                copy(
                    consecutiveCorrectCount = 0,
                    feedback = feedback
                )
            }
        }
    }
    
    private fun tick() {
        val newTime = state.value.timeLeftSeconds - 1
        if (newTime <= 0) {
            stopTimer()
            updateState {
                copy(
                    timeLeftSeconds = 0,
                    feedback = state.value.currentQuestion?.let { 
                        QuizFeedback.Incorrect(it.correctIndices) 
                    }
                )
            }
        } else {
            updateState { copy(timeLeftSeconds = newTime) }
        }
    }
    
    private fun startTimer() {
        stopTimer()
        timerJob = viewModelScope.launch {
            while (state.value.timeLeftSeconds > 0 && state.value.feedback == null) {
                delay(1000)
                send(QuizIntent.Tick)
            }
        }
    }
    
    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
    
    private fun endGame() {
        stopTimer()
        updateState {
            copy(
                isGameActive = false,
                currentQuestion = null,
                feedback = null
            )
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
