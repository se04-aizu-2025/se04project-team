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
                feedback = null,
                consecutiveCorrectCount = 0,
                longestCorrectStreak = 0,
                totalAnsweredQuestions = 0,
                correctAnswers = 0,
                incorrectCounts = emptyMap(),
                showSummary = false
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
                    longestCorrectStreak = maxOf(longestCorrectStreak, consecutiveCorrectCount + 1),
                    totalAnsweredQuestions = totalAnsweredQuestions + 1,
                    correctAnswers = correctAnswers + 1,
                    feedback = dotnet.sort.domain.quiz.model.QuizFeedback.Correct(scoreDelta)
                )
            }
        } else {
            updateState {
                copy(
                    consecutiveCorrectCount = 0,
                    totalAnsweredQuestions = totalAnsweredQuestions + 1,
                    incorrectCounts = incorrectCounts.increment(currentQuestion.algorithmType),
                    feedback = feedback
                )
            }
        }
    }
    
    private fun tick() {
        val newTime = state.value.timeLeftSeconds - 1
        if (newTime <= 0) {
            stopTimer()
            val currentQuestion = state.value.currentQuestion
            updateState {
                copy(
                    timeLeftSeconds = 0,
                    feedback = currentQuestion?.let {
                        QuizFeedback.Incorrect(it.correctIndices)
                    },
                    consecutiveCorrectCount = 0,
                    totalAnsweredQuestions = totalAnsweredQuestions + 1,
                    incorrectCounts =
                        currentQuestion?.let { incorrectCounts.increment(it.algorithmType) }
                            ?: incorrectCounts
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
                feedback = null,
                showSummary = true
            )
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}

private fun Map<SortType, Int>.increment(type: SortType): Map<SortType, Int> {
    val current = this[type] ?: 0
    return this + (type to (current + 1))
}
