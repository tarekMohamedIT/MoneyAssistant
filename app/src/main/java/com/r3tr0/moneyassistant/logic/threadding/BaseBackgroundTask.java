package com.r3tr0.moneyassistant.logic.threadding;

/**
 * Copyright 2018 Tarek Mohamed
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.AsyncTask;

import com.r3tr0.moneyassistant.core.interfaces.IUseCase;
import com.r3tr0.moneyassistant.core.interfaces.OnProcessingEndListener;

public abstract class BaseBackgroundTask<Output>
        extends AsyncTask<Void, Void, Output> {

    private IUseCase<Output> useCase;
    private OnProcessingEndListener<Output> onProcessingEndListener;

    public BaseBackgroundTask() {

    }

    protected BaseBackgroundTask(IUseCase<Output> useCase) {
        this.useCase = useCase;
    }

    public void setUseCase(IUseCase<Output> useCase) {
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
