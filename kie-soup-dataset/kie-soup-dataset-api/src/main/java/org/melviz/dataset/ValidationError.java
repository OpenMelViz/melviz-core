/*
 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.melviz.dataset;

public class ValidationError extends RuntimeException {
    int code = -1;

    public ValidationError(String message) {
        super(message);
    }

    public ValidationError(int code, String message) {
        super(code + " - " + message);
        this.code = code;
    }

    public ValidationError(int code) {
        super(Integer.toString(code));
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}