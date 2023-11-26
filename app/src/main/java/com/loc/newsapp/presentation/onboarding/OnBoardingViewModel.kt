package com.loc.newsapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.usecases.app_entry.AppEntryUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
   private val appEntryUsecase: AppEntryUsecase
): ViewModel() {

    fun OnEvent(event : OnBoardingEvent){
      when(event){
          is OnBoardingEvent.saveAppEntry->{
              saveAppEntry()
          }
      }
    }

    private fun saveAppEntry(){
         viewModelScope.launch {
             appEntryUsecase.saveAppEntry()
         }
    }

}


