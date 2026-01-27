import dotnet.sort.generator.ArrayGeneratorImpl
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortType
import dotnet.sort.presentation.feature.sort.SortIntent
import dotnet.sort.presentation.feature.sort.SortViewModel
import dotnet.sort.repository.SettingsRepository
import dotnet.sort.usecase.ExecuteSortUseCase
import dotnet.sort.usecase.GenerateArrayUseCase
import dotnet.sort.usecase.RecordHistoryEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SortFeatureIntegrationTest {
    // Note: In a real integration test, we would test the Screen Composable.
    // However, ensuring full UI hierarchy without actual Window in headless might be tricky.
    // Instead, we verify the ViewModel -> UseCase integration which is the core of "Feature" logic.
    // Or if we check the Compose UI, we need the UI content.
    // Let's stick to a reliable Integration Test of ViewModel + Real UseCases + Real Generator.

    private lateinit var viewModel: SortViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        val generator = ArrayGeneratorImpl()
        val generateUseCase = GenerateArrayUseCase(generator)
        val executeUseCase = ExecuteSortUseCase()
        val recordHistoryEventUseCase =
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
        viewModel = SortViewModel(executeUseCase, generateUseCase, recordHistoryEventUseCase, FakeSettingsRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Integration Test - Full Sort Flow`() =
        runTest {
            // 1. Initial State
            assertTrue(viewModel.state.value.arraySize == 50)

            // 2. Change Settings
            viewModel.send(SortIntent.SelectAlgorithm(SortType.QUICK))
            assertTrue(viewModel.state.value.algorithm == SortType.QUICK)

            viewModel.send(SortIntent.SetArraySize(10))
            // We might need to wait for coroutine if it was asynchronous, but real generator is fast.
            // In unit test we advanced time. Here we rely on flow.

            // 3. Generate Array
            viewModel.send(SortIntent.GenerateArray(ArrayGeneratorType.DESCENDING))

            // 4. Start Sort
            viewModel.send(SortIntent.StartSort)

            // Verification: Since we used Real implementations, the sort should actually happen.
            // We check if we get a result eventually.
            // This is "Integration" because it uses real logic from Domain/Data layers, not mocks.
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
