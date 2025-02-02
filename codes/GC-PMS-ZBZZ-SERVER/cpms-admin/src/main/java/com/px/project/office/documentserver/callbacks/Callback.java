/**
 *
 * (c) Copyright Ascensio System SIA 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.px.project.office.documentserver.callbacks;

import com.px.project.office.dto.Track;
import org.springframework.beans.factory.annotation.Autowired;

// specify the callback handler functions
public interface Callback {
    int handle(Track body, String fileName);  // handle the callback
    int getStatus();  // get document status
    @Autowired
    default void selfRegistration(CallbackHandler callbackHandler){  // register a callback handler
        callbackHandler.register(getStatus(), this);
    }
}
