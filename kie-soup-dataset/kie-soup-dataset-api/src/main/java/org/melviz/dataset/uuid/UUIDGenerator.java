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
package org.melviz.dataset.uuid;

/**
 * Interface for the generation of UUIDs
 */
public interface UUIDGenerator {

    /**
     * Creates a brand new UUID
     * @return A 36 character length string
     */
    String newUuid();

    /**
     * Creates a brand new UUID in base-64 and without characters forbidden in URLs (plus sign, equal, slash and ampersand)
     * @return A 22 character length, base-64 and URL-safe string
     */
    String newUuidBase64();

    /**
     * Converts an un-encoded 36 character UUID to a base-64 and URL-safe string.
     */
    String uuidToBase64(String str);

    /**
     * Converts back a base-64 and URL-safe string to its original 36 character representation.
     */
    String uuidFromBase64(String str);
}
