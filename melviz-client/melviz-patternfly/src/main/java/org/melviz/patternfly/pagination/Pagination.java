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
package org.melviz.patternfly.pagination;

import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import elemental2.dom.HTMLElement;
import org.uberfire.client.mvp.UberElemental;

@Dependent
public class Pagination {

    @Inject
    View view;

    private Consumer<Integer> onPageChange;

    private int currentPage;

    public interface View extends UberElemental<Pagination> {

        void setup(int nRows,
                  int pageSize);

    }
    
    @PostConstruct
    public void init() {
        view.init(this);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setOnPageChange(Consumer<Integer> onPageChangeAction) {
        this.onPageChange = onPageChangeAction;
    }

    public HTMLElement getElement() {
        return this.view.getElement();
    }

    void selectPage(int pageNumber) {
        this.currentPage = pageNumber;
        if (onPageChange != null) {
            onPageChange.accept(pageNumber);
        }
    }

    public void setPagination(int nRows,
                              int pageSize) {
        view.setup(nRows, pageSize);
    }

}
