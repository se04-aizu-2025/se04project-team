package dotnet.sort.presentation.feature.sort

import app.cash.turbine.test
import dotnet.sort.generator.ArrayGenerator
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortType
import dotnet.sort.repository.SettingsRepository
import dotnet.sort.usecase.ExecuteSortUseCase
import dotnet.sort.usecase.GenerateArrayUseCase
import dotnet.sort.usecase.RecordHistoryEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SortViewModelTest {
    private lateinit var viewModel: SortViewModel
    private lateinit var executeSortUseCase: ExecuteSortUseCase
    private lateinit var generateArrayUseCase: GenerateArrayUseCase
    private lateinit var recordHistoryEventUseCase: RecordHistoryEventUseCase
    private lateinit var settingsRepository: SettingsRepository

    // Fake implementation for ArrayGenerator
    private val fakeArrayGenerator =
        object : ArrayGenerator {
            override fun generate(
                size: Int,
                type: ArrayGeneratorType,
            ): List<Int> = List(size) { it }

            override fun generate(
                size: Int,
                type: ArrayGeneratorType,
                range: IntRange,
            ): List<Int> = List(size) { it }
        }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        executeSortUseCase = ExecuteSortUseCase()
        generateArrayUseCase = GenerateArrayUseCase(fakeArrayGenerator)
        recordHistoryEventUseCase =
            RecordHistoryEventUseCase(
                object : dotnet.sort.repository.AlgorithmHistoryRepository {
                    override suspend fun recordEvent(
                        algorithmType: SortType?,
                        eventType: dotnet.sort.model.HistoryEventType,
                        metadata: String?,
                    ) = Unit

                    override fun observeRecentEvents(limit: Int) =
                        kotlinx.coroutines.flow.flow {
                            emit(emptyList<dotnet.sort.model.AlgorithmHistoryEntry>())
                        }
                },
            )
        settingsRepository = FakeSettingsRepository()
        viewModel = SortViewModel(executeSortUseCase, generateArrayUseCase, recordHistoryEventUseCase, settingsRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN initial state WHEN initialized THEN has default values`() =
        runTest {
            val state = viewModel.state.value
            assertEquals(SortType.BUBBLE, state.algorithm)
            assertEquals(50, state.arraySize)
            assertFalse(state.isLoading)
        }

    @Test
    fun `GIVEN SelectAlgorithm intent WHEN dispatched THEN updates algorithm`() =
        runTest {
            viewModel.send(SortIntent.SelectAlgorithm(SortType.QUICK))
            assertEquals(SortType.QUICK, viewModel.state.value.algorithm)
        }

    @Test
    fun `GIVEN SetArraySize intent WHEN dispatched THEN updates size and regenerates array`() =
        runTest {
            viewModel.send(SortIntent.SetArraySize(20))

            val state = viewModel.state.value
            assertEquals(20, state.arraySize)
            assertEquals(20, state.currentNumbers.size)
        }

    @Test
    fun `GIVEN StartSort intent WHEN dispatched THEN updates state to sorting and completes`() =
        runTest {
            // Prepare
            viewModel.send(SortIntent.GenerateArray(ArrayGeneratorType.RANDOM))

            // Start Sort
            viewModel.send(SortIntent.StartSort)

            // With UnconfinedTestDispatcher, the sort execution (which is on viewModelScope)
            // should start immediately.
            // The delays in startAutoPlay are skipped by runTest?
            // Actually, with Unconfined, we rely on the fact that coroutines run eagerly.
            // We verify that the final state has a result.

            val state = viewModel.state.value
            assertFalse(state.isLoading)
            assertTrue(state.sortResult != null)
        }
}

private class FakeSettingsRepository : SettingsRepository {
    private val _isDarkTheme = MutableStateFlow(false)
    private val _language = MutableStateFlow(dotnet.sort.domain.model.Language.ENGLISH)
    private val _barTheme = MutableStateFlow(dotnet.sort.domain.model.BarColorTheme.KOTLIN)
    private val _isSoundEnabled = MutableStateFlow(true)
    private val _soundVolume = MutableStateFlow(0.6f)

    override val isDarkTheme: Flow<Boolean> = _isDarkTheme.asStateFlow()
    override val language: Flow<dotnet.sort.domain.model.Language> = _language.asStateFlow()
    override val barTheme: Flow<dotnet.sort.domain.model.BarColorTheme> = _barTheme.asStateFlow()
    override val isSoundEnabled: Flow<Boolean> = _isSoundEnabled.asStateFlow()
    override val soundVolume: Flow<Float> = _soundVolume.asStateFlow()

    override suspend fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }

    override suspend fun setLanguage(language: dotnet.sort.domain.model.Language) {
        _language.value = language
    }

    override suspend fun setBarTheme(theme: dotnet.sort.domain.model.BarColorTheme) {
        _barTheme.value = theme
    }

    override suspend fun setSoundEnabled(enabled: Boolean) {
        _isSoundEnabled.value = enabled
    }

    override suspend fun setSoundVolume(volume: Float) {
        _soundVolume.value = volume
    }
}
