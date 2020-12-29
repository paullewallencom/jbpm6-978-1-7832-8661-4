/*
 * Copyright 2012 JBoss Inc
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

package org.jbpm.console.ng.client.navbar;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RequiresResize;
import javax.enterprise.event.Observes;
import org.jbpm.console.ng.client.AppResource;
import org.kie.workbench.common.widgets.client.search.ClearSearchEvent;
import org.kie.workbench.common.widgets.client.search.ContextualSearch;
import org.kie.workbench.common.widgets.client.search.SetSearchTextEvent;
import org.uberfire.client.workbench.widgets.menu.PespectiveContextMenusPresenter;

/**
 * A stand-alone (i.e. devoid of Workbench dependencies) View
 */
public class ComplementNavAreaView
        extends Composite
        implements RequiresResize,
                   ComplementNavAreaPresenter.View {

    interface ViewBinder
            extends
            UiBinder<Panel, ComplementNavAreaView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @UiField(provided = true)
    public Image logo;

    @UiField
    public FlowPanel contextMenuArea;

    @UiField
    public Button searchButton;
    
    @UiField
    public TextBox searchTextBox;
    
    @Inject
    private PespectiveContextMenusPresenter contextMenu;
    
    @Inject
    private ContextualSearch contextualSearch;

    @PostConstruct
    public void init() {
        logo = new Image( AppResource.INSTANCE.images().logo() );

        initWidget( uiBinder.createAndBindUi( this ) );
        contextMenuArea.add( contextMenu.getView() );
    }

    @Override
    public void onResize() {
        int height = getParent().getOffsetHeight();
        int width = getParent().getOffsetWidth();
//        panel.setPixelSize( width, height );
    }
    
    @UiHandler("searchButton")
    public void search(ClickEvent e){
        contextualSearch.getSearchBehavior().execute(searchTextBox.getText());
    }
    
    public void onClearSearchBox(@Observes ClearSearchEvent clearSearch){
        searchTextBox.setText("");
    }
    
    public void onSetSearchText(@Observes SetSearchTextEvent setSearchText){
        searchTextBox.setText(setSearchText.getSearchText());
    }

}