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

package org.melviz.client.resources.i18n;

public class AppConstants {

    private AppConstants() {
        // empty
    }

    public static String loadingDashboards() {
        return "Loading dashboard";
    }

    public static String uploadingDashboards() {
        return "Uploading Loading dashboards";
    }

    public static String notFoundDashboard(String perspectiveName) {
        return "Dashboard " + perspectiveName + " not found. Please review the dashboard name and try again.";
    }

    public static String importSuccess(String fileName) {
        return "Dashboard " + fileName + " successfully imported";
    }

    public static String couldNotConnectToServer() {
        return "Could not connect to server. This very likely means a network problem.";
    }

    public static String invalidBusResponseProbablySessionTimeout() {
        return "Invalid response received from the server. This very likely means that you have been logged out due to inactivity.";
    }

    public static String notAuthorized() {
        return "Not Authorized";
    }

    public static String notAbleToLoadDashboard(String message) {
        return "Not able to load dashboard: " + message;
    }

    public static String emptyEditorMode() {
        return "No content to display. Start editing to see the result here.";
    }

    public static String emptyClientMode() {
        return "Dashboards were not imported. You can import a dashboard by creating a supported YAML/JSON file";
    }

    public static String emptyWithImportId(String modelId) {
        return "Not able to load <strong>" + modelId +
                "</strong>. You can import a dashboard by creating a supported YAML/JSON file";
    }

    public static String errorContentTitle() {
        return "Error loading content";
    }

    public static String emptyScreenTrySamples() {
        return "or you can try samples below.";
    }

}
