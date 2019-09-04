package sti.com.livefeedback.ui.feed;

import sti.com.livefeedback.data.DataManager;
import sti.com.livefeedback.ui.base.BaseViewModel;
import sti.com.livefeedback.utils.rx.SchedulerProvider;

/**
 * Created by Jyoti on 29/07/17.
 */

public class FeedViewModel extends BaseViewModel {

    public FeedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
