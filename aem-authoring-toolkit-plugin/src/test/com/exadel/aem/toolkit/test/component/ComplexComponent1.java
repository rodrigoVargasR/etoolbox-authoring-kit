/*
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exadel.aem.toolkit.test.component;

import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOn;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnRef;
import com.exadel.aem.toolkit.api.annotations.container.PlaceOnTab;
import com.exadel.aem.toolkit.api.annotations.container.Tab;
import com.exadel.aem.toolkit.api.annotations.editconfig.ActionConstants;
import com.exadel.aem.toolkit.api.annotations.editconfig.DropTargetConfig;
import com.exadel.aem.toolkit.api.annotations.editconfig.EditConfig;
import com.exadel.aem.toolkit.api.annotations.editconfig.EditorType;
import com.exadel.aem.toolkit.api.annotations.editconfig.InplaceEditingConfig;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Extends;
import com.exadel.aem.toolkit.api.annotations.widgets.FieldSet;
import com.exadel.aem.toolkit.api.annotations.widgets.NumberField;
import com.exadel.aem.toolkit.api.annotations.widgets.Switch;
import com.exadel.aem.toolkit.api.annotations.widgets.TextField;
import com.exadel.aem.toolkit.api.annotations.widgets.rte.RichTextEditor;
import com.exadel.aem.toolkit.api.annotations.widgets.rte.RteFeatures;
import com.exadel.aem.toolkit.api.annotations.widgets.select.Option;
import com.exadel.aem.toolkit.api.annotations.widgets.select.Select;
import com.exadel.aem.toolkit.core.util.TestConstants;

import static com.exadel.aem.toolkit.test.component.ComplexComponent1.TAB_1;
import static com.exadel.aem.toolkit.test.component.ComplexComponent1.TAB_2;
import static com.exadel.aem.toolkit.test.component.ComplexComponent1.TAB_3;
import static com.exadel.aem.toolkit.test.component.ComplexComponent1.TAB_4;
import static com.exadel.aem.toolkit.test.component.ComplexComponent1.TAB_5;
import static com.exadel.aem.toolkit.test.component.ComplexComponent1.TAB_6;
import static com.exadel.aem.toolkit.test.component.ComplexComponent1.TAB_7;

@Dialog(
        name = TestConstants.DEFAULT_COMPONENT_NAME,
        title = TestConstants.DEFAULT_COMPONENT_TITLE,
        description = "test component",
        componentGroup = TestConstants.DEFAULT_COMPONENT_GROUP,
        resourceSuperType = "resource/super/type",
        disableTargeting = true,
        isContainer = true,
        width = 800,
        extraClientlibs = {
                "cq.common.wcm",
                "core.wcm.page.properties",
                "cq.wcm.msm.properties"
        },
        tabs = {
                @Tab(title = TAB_1),
                @Tab(title = TAB_2),
                @Tab(title = TAB_3),
                @Tab(title = TAB_4),
                @Tab(title = TAB_5),
                @Tab(title = TAB_6),
                @Tab(title = TAB_7)
        }
)
@EditConfig(
        actions = {
                ActionConstants.EDIT,
                ActionConstants.COPYMOVE,
                ActionConstants.INSERT,
                ActionConstants.DELETE
        },
        dropTargets = {
                @DropTargetConfig(
                        nodeName = "target1",
                        accept = {"targets/.*"},
                        groups = {"TargetsGroup"},
                        propertyName = "drop_target1/reference"
                ),
                @DropTargetConfig(
                        nodeName = "target2",
                        accept = {"targets/.*"},
                        groups = {"TargetsGroup"},
                        propertyName = "drop_target2/reference"
                ),
                @DropTargetConfig(
                        nodeName = "target3",
                        accept = {"targets/.*"},
                        groups = {"TargetsGroup"},
                        propertyName = "drop_target3/reference"
                ),
        },
        inplaceEditing = {
                @InplaceEditingConfig(
                        title = "Title",
                        propertyName = "title",
                        type = EditorType.TITLE,
                        editElementQuery = ".editable-title"
                ),
                @InplaceEditingConfig(
                        title = "Description",
                        propertyName = "description",
                        type = EditorType.TEXT,
                        editElementQuery = ".editable-description",
                        richText = @Extends(value = ComplexComponent1.class, field = "secondField")
                ),
                @InplaceEditingConfig(
                        title = "Primary Topic 1 Title",
                        propertyName = "primary1_topicTitle",
                        type = "Primary_type",
                        editElementQuery = ".editable-prtopic1title"
                ),
                @InplaceEditingConfig(
                        title = "Primary Topic 1 Description",
                        propertyName = "primary1_topicDescription",
                        type = EditorType.TEXT,
                        editElementQuery = ".editable-prtopic1description",
                        richText = @Extends(value = SampleFieldsetAncestor.class, field = "description")
                ),
                @InplaceEditingConfig(
                        title = "Primary Topic 2 Title",
                        propertyName = "primary2_topicTitle",
                        type = "Primary_type",
                        editElementQuery = ".editable-prtopic2title"
                ),
                @InplaceEditingConfig(
                        title = "Primary Topic 2 Description",
                        propertyName = "primary2_topicDescription",
                        type = EditorType.TEXT,
                        editElementQuery = ".editable-prtopic2description",
                        richText = @Extends(value = SampleFieldsetAncestor.class, field = "description")
                ),
                @InplaceEditingConfig(
                        title = "Primary Topic 3 Title",
                        propertyName = "primary3_topicTitle",
                        type = "Primary_type",
                        editElementQuery = ".editable-prtopic3title"
                ),
                @InplaceEditingConfig(
                        title = "Primary Topic 3 Description",
                        propertyName = "primary3_topicDescription",
                        type = EditorType.TEXT,
                        editElementQuery = ".editable-prtopic3description",
                        richText = @Extends(value = SampleFieldsetAncestor.class, field = "description")
                ),
                @InplaceEditingConfig(
                        title = "Secondary Topic 1 Title",
                        propertyName = "secondary1_topicTitle",
                        type = "Secondary_type",
                        editElementQuery = ".editable-sectopic1title"
                ),
                @InplaceEditingConfig(
                        title = "Secondary Topic 1 Description",
                        propertyName = "secondary1_topicDescription",
                        type = EditorType.TEXT,
                        editElementQuery = ".editable-sectopic1description",
                        richText = @Extends(value = SampleFieldsetAncestor.class, field = "description")
                ),
                @InplaceEditingConfig(
                        title = "Secondary Topic 2 Title",
                        propertyName = "secondary2_topicTitle",
                        type = "Secondary_type",
                        editElementQuery = ".editable-sectopic2title"
                ),
                @InplaceEditingConfig(
                        title = "Secondary Topic 2 Description",
                        propertyName = "secondary2_topicDescription",
                        type = EditorType.TEXT,
                        editElementQuery = ".editable-sectopic2description",
                        richText = @Extends(value = SampleFieldsetAncestor.class, field = "description")
                ),
        }
)
@SuppressWarnings("unused")
public class ComplexComponent1 {
    static final String TAB_1 = "Tab_1";
    static final String TAB_2 = "Tab_2";
    static final String TAB_3 = "Tab_3";
    static final String TAB_4 = "Tab_4";
    static final String TAB_5 = "Tab_5";
    static final String TAB_6 = "Tab_6";
    static final String TAB_7 = "Tab_7";

    private static final String PREFIX_FIRST_PRIMARY_DIALOG = "primary1";
    private static final String PREFIX_SECOND_PRIMARY_DIALOG = "primary2";
    private static final String PREFIX_THIRD_PRIMARY_DIALOG = "primary3";

    private static final String PREFIX_FIRST_SECONDARY_DIALOG = "secondary1";
    private static final String PREFIX_SECOND_SECONDARY_DIALOG = "secondary2";

    private static final String TITLE_FIRST_SECONDARY_DIALOG = "Additional Topic #1";
    private static final String TITLE_SECOND_SECONDARY_DIALOG = "Additional Topic #2";

    private static final String FIELD_FIRST_SECONDARY_DIALOG_ENABLED = "firstSecondaryDialogEnabled";

    private static final String FIELD_SECOND_SECONDARY_DIALOG_ENABLED = "secondSecondaryDialogEnabled";

    private static final String LABEL_SECONDARY_DIALOG_ENABLED = "Enable Additional Topic?";

    @DialogField(
            name = "first_field",
            label = "First field label",
            description = "First field description",
            validation = "foundation.jcr.name",
            required = true
    )
    @TextField
    @PlaceOnTab(TAB_1)
    private String firstField;

    @DialogField(
            name = "second_field",
            label = "Second field label",
            description = "Second field description",
            validation = {"foundation.jcr.name1", "foundation.jcr.name2"}
    )
    @RichTextEditor(
            features = {
                    RteFeatures.LINKS_MODIFYLINK,
                    RteFeatures.LINKS_UNLINK,
                    RteFeatures.SUBSUPERSCRIPT_SUBSCRIPT,
                    RteFeatures.SUBSUPERSCRIPT_SUPERSCRIPT
            }
    )
    @PlaceOnTab(TAB_1)
    private String secondField;

    @DialogField(
            name = "third_Field",
            label = "Third field label",
            description = "Third field description",
            required = true
    )
    @Select(
            options = {
                    @Option(text = "First", value = "1"),
                    @Option(text = "Second", value = "2", selected = true),
                    @Option(text = "Third", value = "3"),
                    @Option(text = "Fourth", value = "4")
            })
    @PlaceOnTab(TAB_1)
    private String thirdField;

    @DialogField
    @FieldSet(namePrefix = PREFIX_FIRST_PRIMARY_DIALOG)
    private SampleFieldsetBase2 firstPrimaryDialog;

    @DialogField
    @FieldSet(namePrefix = PREFIX_SECOND_PRIMARY_DIALOG)
    private SampleFieldsetBase2 secondPrimaryDialog;

    @DialogField
    @FieldSet(namePrefix = PREFIX_THIRD_PRIMARY_DIALOG)
    private SampleFieldsetBase2 thirdPrimaryDialog;

    @DialogField(
            name = FIELD_FIRST_SECONDARY_DIALOG_ENABLED,
            label = LABEL_SECONDARY_DIALOG_ENABLED
    )
    @Switch
    @DependsOnRef(name = "first")
    private boolean firstSecondaryDialogEnabled;

    @DialogField
    @FieldSet(
            title = TITLE_FIRST_SECONDARY_DIALOG,
            namePrefix = PREFIX_FIRST_SECONDARY_DIALOG
    )
    @DependsOn(query = "@first")
    private SampleFieldsetAncestor firstSecondaryDialog;

    @DialogField(
            name = FIELD_SECOND_SECONDARY_DIALOG_ENABLED,
            label = LABEL_SECONDARY_DIALOG_ENABLED
    )
    @Switch
    private boolean secondSecondaryDialogEnabled;

    @DialogField
    @FieldSet(
            title = TITLE_SECOND_SECONDARY_DIALOG,
            namePrefix = PREFIX_SECOND_SECONDARY_DIALOG
    )
    private SampleFieldsetAncestor secondSecondaryDialog;

    @DialogField(
            name = "first_number_field",
            label = "First number field label",
            description = "First number field description"
    )
    @TextField
    @NumberField(min = 0)
    @PlaceOnTab("Tab_6")
    private Integer sampleFirstNumberField;

    @DialogField(
            name = "second_number_field",
            label = "Second number field label",
            description = "Second number field description"
    )
    @TextField
    @NumberField(min = 0)
    @PlaceOnTab("Tab_6")
    private Integer sampleSecondNumberField;

    @DialogField
    @FieldSet
    @PlaceOnTab("Tab_7")
    private SampleFieldsetBase3 sampleFieldSet;
}