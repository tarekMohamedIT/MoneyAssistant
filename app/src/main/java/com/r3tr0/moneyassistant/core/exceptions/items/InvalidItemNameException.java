package com.r3tr0.moneyassistant.core.exceptions.items;

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
 * A runtime exception when the item's name is invalid.
 */
public class InvalidItemNameException extends RuntimeException {
    public InvalidItemNameException() {
        super("The item's name is invalid");
    }

    public InvalidItemNameException(Throwable cause) {
        super("The item's name is invalid", cause);
    }
}
