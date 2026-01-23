package dotnet.sort.presentation.feature.learn

import dotnet.sort.domain.model.SortType
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LearnViewModelTest {
    private lateinit var viewModel: LearnViewModel

    @Before
    fun setup() {
        viewModel = LearnViewModel()
    }

    @Test
    fun `GIVEN initial state WHEN initialized THEN navigationTarget is null`() {
        // Given & When (initialized)
        
        // Then
        assertNull(viewModel.state.value.navigationTarget)
    }

    @Test
    fun `GIVEN SelectAlgorithm intent WHEN dispatched THEN updates navigationTarget`() {
        // When
        viewModel.send(LearnIntent.SelectAlgorithm(SortType.QUICK))
        
        // Then
        assertEquals(SortType.QUICK, viewModel.state.value.navigationTarget)
    }
}
