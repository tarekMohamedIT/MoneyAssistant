package com.r3tr0.moneyassistant.core.interfaces;

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

/**
 * -An interface that is used for background operations.
 * -Paired with the {@link OnProcessingEndListener} interface, You can make rxjava-like connection
 * to a thread to finish processing, then show the processing on the UI thread.
 * <p>
 * -IE : {@link com.r3tr0.moneyassistant.logic.threadding.GetAllItemsTask}
 *
 * @param <Output> The output that the background processing should return.
 */
public interface IUseCase<Output> {

    /**
     * A method that should define what to do in the background.
     * @return The result of the operation.
     */
    Output processInBackground();
}
