package sti.com.livefeedback;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import sti.com.livefeedback.data.DataManager;
import sti.com.livefeedback.ui.about.AboutViewModel;
import sti.com.livefeedback.ui.feed.FeedViewModel;
import sti.com.livefeedback.ui.feed.blogs.BlogViewModel;
import sti.com.livefeedback.ui.feed.opensource.OpenSourceViewModel;
import sti.com.livefeedback.ui.filter.FilterViewModel;
import sti.com.livefeedback.ui.home.HomeViewModel;
import sti.com.livefeedback.ui.login.LoginViewModel;
import sti.com.livefeedback.ui.main.MainViewModel;
import sti.com.livefeedback.ui.main.rating.RateUsViewModel;
import sti.com.livefeedback.ui.main.stores.StoreViewModel;
import sti.com.livefeedback.ui.productdetails.ProductDetailsViewModel;
import sti.com.livefeedback.ui.profile.ProfileViewModel;
import sti.com.livefeedback.ui.register.RegisterViewModel;
import sti.com.livefeedback.ui.splash.SplashViewModel;
import sti.com.livefeedback.ui.storedetails.StoreDetailsViewModel;
import sti.com.livefeedback.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jyotidubey on 22/02/19.
 */
@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

  private final DataManager dataManager;
  private final SchedulerProvider schedulerProvider;

  @Inject
  public ViewModelProviderFactory(DataManager dataManager,
      SchedulerProvider schedulerProvider) {
    this.dataManager = dataManager;
    this.schedulerProvider = schedulerProvider;
  }


  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    if (modelClass.isAssignableFrom(AboutViewModel.class)) {
      //noinspection unchecked
      return (T) new AboutViewModel(dataManager,schedulerProvider);
    } else if (modelClass.isAssignableFrom(FeedViewModel.class)) {
      //noinspection unchecked
      return (T) new FeedViewModel(dataManager,schedulerProvider);
    } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
      //noinspection unchecked
      return (T) new LoginViewModel(dataManager,schedulerProvider);
    } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
      //noinspection unchecked
      return (T) new MainViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(BlogViewModel.class)) {
      //noinspection unchecked
      return (T) new BlogViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(RateUsViewModel.class)) {
      //noinspection unchecked
      return (T) new RateUsViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(OpenSourceViewModel.class)) {
      //noinspection unchecked
      return (T) new OpenSourceViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
      //noinspection unchecked
      return (T) new SplashViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
      //noinspection unchecked
      return (T) new HomeViewModel(dataManager,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(StoreViewModel.class)) {
      //noinspection unchecked
      return (T) new StoreViewModel(dataManager,schedulerProvider);
    }else if (modelClass.isAssignableFrom(FilterViewModel.class)) {
      //noinspection unchecked
      return (T) new FilterViewModel(dataManager,schedulerProvider);
    }else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
        //noinspection unchecked
        return (T) new RegisterViewModel(dataManager,schedulerProvider);
    }else if (modelClass.isAssignableFrom(StoreDetailsViewModel.class)) {
      //noinspection unchecked
      return (T) new StoreDetailsViewModel(dataManager,schedulerProvider);
    }else if (modelClass.isAssignableFrom(ProductDetailsViewModel.class)) {
      //noinspection unchecked
      return (T) new ProductDetailsViewModel(dataManager,schedulerProvider);
    }else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
      //noinspection unchecked
      return (T) new ProfileViewModel(dataManager,schedulerProvider);
    }





    throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
  }
}