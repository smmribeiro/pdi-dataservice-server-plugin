/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2024 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.dataservice.ui.menu;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.dataservice.DataServiceContext;
import org.pentaho.di.trans.dataservice.DataServiceMeta;
import org.pentaho.di.trans.dataservice.ui.DataServiceDelegate;
import org.pentaho.di.ui.core.gui.GUIResource;
import org.pentaho.di.ui.spoon.SelectionTreeExtension;
import org.pentaho.di.ui.spoon.Spoon;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith( MockitoJUnitRunner.StrictStubs.class)
public class DataServiceViewTreeExtensionTest {

  private static String DATA_SERVICE_NAME = "Data Service Name";

  @Mock
  DataServiceContext context;

  @Mock
  DataServiceDelegate delegate;

  @Mock
  LogChannelInterface log;

  @Mock
  SelectionTreeExtension selectionTreeExtension;

  @Mock
  TransMeta transMeta;

  @Mock
  TreeItem treeItem;

  @Mock
  GUIResource guiResource;

  Display display;

  Image image;

  @Mock
  DataServiceMeta dataServiceMeta;

  DataServiceViewTreeExtension dataServiceViewTreeExtension;
  DataServiceViewTreeExtension dataServiceViewTreeExtensionSpy;
  List<DataServiceMeta> dataServiceMetaList = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    when( context.getDataServiceDelegate() ).thenReturn( delegate );

    dataServiceViewTreeExtension = new DataServiceViewTreeExtension( context );
    dataServiceViewTreeExtensionSpy = spy( new DataServiceViewTreeExtension( context ) );

    dataServiceMetaList.add( dataServiceMeta );
  }

  @Test
  public void testCallExtension() throws Exception {
    when( selectionTreeExtension.getAction() ).thenReturn( Spoon.EDIT_SELECTION_EXTENSION );
    when( selectionTreeExtension.getSelection() ).thenReturn( dataServiceMeta );

    dataServiceViewTreeExtension.callExtensionPoint( log, selectionTreeExtension );

    verify( selectionTreeExtension ).getSelection();
    verify( delegate ).editDataService( dataServiceMeta );
  }

  @Test
  public void testCallExtensionWithRefreshTree() throws Exception {
    doNothing().when( dataServiceViewTreeExtensionSpy ).refreshTree(
        any( SelectionTreeExtension.class ) );

    when( selectionTreeExtension.getAction() ).thenReturn( Spoon.REFRESH_SELECTION_EXTENSION );
    when( selectionTreeExtension.getMeta() ).thenReturn( transMeta );
    dataServiceViewTreeExtensionSpy.callExtensionPoint( log, selectionTreeExtension );

    verify( selectionTreeExtension ).getMeta();
    verify( dataServiceViewTreeExtensionSpy ).refreshTree( any( SelectionTreeExtension.class ) );
  }
}
