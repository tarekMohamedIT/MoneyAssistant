package com.r3tr0.moneyassistant.logic.threadding;

import android.os.AsyncTask;

import com.r3tr0.moneyassistant.core.interfaces.IUseCase;
import com.r3tr0.moneyassistant.core.interfaces.OnProcessingEndListener;

public abstract class BaseBackgroundTask<Output>
        extends AsyncTask<Void, Void, Output> {

    private IUseCase<Output> useCase;
    private OnProcessingEndListener<Output> onProcessingEndListener;

    protected BaseBackgroundTask(IUseCase<Output> useCase) {
        this.useCase = useCase;
    }

    public void setOnProcessingEndListener(OnProcessingEndListener<Output> onProcessingEndListener) {
        this.onProcessingEndListener = onProcessingEndListener;
    }

    @Override
    protected final Output doInBackground(Void... voids) {
        return useCase.processInBackground();
    }

    @Override
    protected final void onPostExecute(Output output) {
        if (onProcessingEndListener != null)
            onProcessingEndListener.onProcessingEnd(output);
    }

    public void execute() {
        super.execute();
    }
}
