package com.flight.app.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.flight.app.view.map.MapActivityView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE, sdk = [21])
class MapViewModelTest {

    private lateinit var viewModel: MapViewModel

    @Spy
    private val spyContext: Context = Robolectric.buildActivity(Activity::class.java).get()
    @Mock
    private lateinit var mockView: MapActivityView
    @Mock
    private lateinit var mockGoogleApiAvailability: GoogleApiAvailability
    @Mock
    private lateinit var mockApplication: Application
    @Mock
    private lateinit var mockPermissionChecker: PermissionChecker

    @Before
    fun setUp() {
        initMocks(this)

        viewModel = MapViewModel(mockView, mockGoogleApiAvailability, mockApplication)
    }

    @Test
    fun on_construction_GoogleApiAvailability_should_be_initialized() {
        assertNotNull(MapViewModel(mockView, mockApplication).googleApiAvailability)
    }

    @Test
    fun check_if_isGooglePlayServicesAvailable_is_invoked_when_checkForGooglePlayServices_executes() {
        viewModel.checkForGooglePlayServices()

        verify(mockGoogleApiAvailability).isGooglePlayServicesAvailable(mockApplication)
    }

    @Test
    fun showUserResolvableError_if_GoogleApiAvailability_isUserResolvableError_return_true() {
        `when`(mockGoogleApiAvailability.isGooglePlayServicesAvailable(mockApplication)).thenReturn(ConnectionResult.SERVICE_MISSING)

        viewModel.checkForGooglePlayServices()

        verify(mockView).displayUserResolvableError(mockGoogleApiAvailability, ConnectionResult.SERVICE_MISSING)
    }

    @Test
    fun showNotSupportedError_if_GoogleApiAvailability_isUserResolvableError_return_false() {
        `when`(mockGoogleApiAvailability.isGooglePlayServicesAvailable(mockApplication)).thenReturn(ConnectionResult.UNKNOWN)

        viewModel.checkForGooglePlayServices()

        verify(mockView).displayNotSupportedError()
    }

    @Test
    fun requestLocationPermission_if_location_permission_is_not_granted() {
        `when`(mockGoogleApiAvailability.isGooglePlayServicesAvailable(mockApplication)).thenReturn(ConnectionResult.SUCCESS)
        `when`(mockPermissionChecker.checkSelfPermission(mockApplication)).thenReturn(PackageManager.PERMISSION_DENIED)

        viewModel.checkForGooglePlayServices()

        verify(mockView).requestLocationPermission()
    }

    @Test
    fun displayMap_if_location_permission_is_granted() {
        `when`(mockGoogleApiAvailability.isGooglePlayServicesAvailable(mockApplication)).thenReturn(ConnectionResult.SUCCESS)
        `when`(mockPermissionChecker.checkSelfPermission(mockApplication)).thenReturn(PackageManager.PERMISSION_GRANTED)

        viewModel.checkForGooglePlayServices()

        verify(mockView).displayMap()
    }

    open class PermissionChecker {

        fun checkSelfPermission(context: Context): Int {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }

}