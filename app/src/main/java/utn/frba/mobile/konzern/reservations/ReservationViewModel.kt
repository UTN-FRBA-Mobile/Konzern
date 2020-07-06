package utn.frba.mobile.konzern.reservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import utn.frba.mobile.konzern.reservations.model.Amenity
import utn.frba.mobile.konzern.reservations.model.Reservation
import utn.frba.mobile.konzern.reservations.repository.AmenityRepository
import utn.frba.mobile.konzern.reservations.repository.ReservationRepository
import utn.frba.mobile.konzern.utils.BaseViewModel
import utn.frba.mobile.konzern.utils.serviceManager.SingleLiveEvent

class ReservationViewModel : BaseViewModel() {
    private val amenityRepository: AmenityRepository = AmenityRepository()
    private val reservationRepository: ReservationRepository = ReservationRepository()

    val amenities = MutableLiveData<List<Amenity>>()
    lateinit var amenitiesList: List<Amenity>

    val dayReservations = MutableLiveData<List<Reservation>>()
    private val _saveEvent = SingleLiveEvent<Any>()
    val saveEvent: LiveData<Any> get() = _saveEvent

    var amenityReserved = false

    fun initAmenities() {
        if(amenities.value == null){
            loadAmenities()
        }
    }

    fun initDayReservations(date: String): Boolean{
        return loadDayReservations(date)
    }

    fun amenityReserved(amenity: String, day: String, hour: String) {
        checkAmenityReserved(amenity, day, hour) {
            _saveEvent.call()
        }
    }

    private fun checkAmenityReserved(amenity: String, day: String, hour: String, continuation: () -> Unit = {}) {
        this.launchControlledInBg(mainOperation = {
            amenityReserved = reservationRepository.exists(
                    getByName(amenity).id,
                    day,
                    hour.toInt()
                )
            },
            continuation = continuation
        )
    }

    fun saveReservation(amenity: String, day: String, hour: String){
        this.launchControlledInBg(mainOperation = {
            reservationRepository.save(
                getByName(amenity).id,
                day,
                hour.toInt()
            )
            // loadReservations()
        })
    }

    fun deleteReservation(id: String, date: String) {
        this.launchControlledInBg( {
            reservationRepository.deleteReservation(id)
            loadDayReservations(date)
        })
    }

    fun getByName(name: String): Amenity {
        for (amenity in amenitiesList) {
            if (amenity.name == name)
                return amenity
        }
        return Amenity()
    }

    fun getById(id: String): Amenity {
        if (amenitiesList != null) {
            for (amenity in amenitiesList) {
                if (amenity.id == id)
                    return amenity
            }
        }
        return Amenity()
    }

    private fun loadAmenities(){
        this.launchControlledInBg(mainOperation = {
            val amenitiesResponse = amenityRepository.getItemList()
            amenities.postValue(amenitiesResponse)
            amenitiesList = amenitiesResponse
        })
    }

    private fun loadDayReservations(date: String) : Boolean{
        this.launchControlledInBg(mainOperation = {
            val response = reservationRepository.fetchByDate(date)
            dayReservations.postValue(response)
            //amenitiesList = response
        })
        return true
    }
}