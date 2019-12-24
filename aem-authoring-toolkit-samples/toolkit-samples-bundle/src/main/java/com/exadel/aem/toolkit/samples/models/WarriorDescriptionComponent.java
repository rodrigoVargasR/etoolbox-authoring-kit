package com.exadel.aem.toolkit.samples.models;

import com.day.cq.tagging.TagManager;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOn;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnRef;
import com.exadel.aem.toolkit.api.annotations.assets.dependson.DependsOnTab;
import com.exadel.aem.toolkit.api.annotations.container.PlaceOnTab;
import com.exadel.aem.toolkit.api.annotations.container.Tab;
import com.exadel.aem.toolkit.api.annotations.main.Dialog;
import com.exadel.aem.toolkit.api.annotations.widgets.Checkbox;
import com.exadel.aem.toolkit.api.annotations.widgets.DialogField;
import com.exadel.aem.toolkit.api.annotations.widgets.Hidden;
import com.exadel.aem.toolkit.api.annotations.widgets.autocomplete.Autocomplete;
import com.exadel.aem.toolkit.api.annotations.widgets.autocomplete.AutocompleteDatasource;
import com.exadel.aem.toolkit.api.annotations.widgets.common.TypeHint;
import com.exadel.aem.toolkit.api.annotations.widgets.datepicker.DatePicker;
import com.exadel.aem.toolkit.api.annotations.widgets.datepicker.DatePickerType;
import com.exadel.aem.toolkit.api.annotations.widgets.datepicker.DateTimeValue;
import com.exadel.aem.toolkit.api.annotations.widgets.radio.RadioButton;
import com.exadel.aem.toolkit.api.annotations.widgets.radio.RadioGroup;
import com.exadel.aem.toolkit.api.annotations.widgets.rte.*;
import com.exadel.aem.toolkit.samples.constants.PathConstants;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Path;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;

@Dialog(
        name = "content/warrior-description-component",
        title = "Warrior description component",
        description = "Tell us about your warrior",
        resourceSuperType = PathConstants.FOUNDATION_PARBASE_PATH,
        componentGroup = "Toolkit Samples",
        tabs= {
                @Tab(title = WarriorDescriptionComponent.TAB_MAIN),
                @Tab(title = WarriorDescriptionComponent.TAB_TASTES),
                @Tab(title = WarriorDescriptionComponent.TAB_FRUITS),
                @Tab(title = WarriorDescriptionComponent.TAB_FILMS)
        }
)
@DependsOnTab(tabTitle = WarriorDescriptionComponent.TAB_FRUITS, query = "@isLikeFruits")
@DependsOnTab(tabTitle = WarriorDescriptionComponent.TAB_FILMS, query = "@isLikeFilms")
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WarriorDescriptionComponent {

    public static final String TAB_MAIN = "Main info";
    public static final String TAB_TASTES = "Tastes";
    public static final String TAB_FRUITS = "Fruits";
    public static final String TAB_FILMS = "Films";
    public static final String EXTERNAL_STYLE_PATH = "/apps/authoring-toolkit/samples/components/content/warrior-description-component/clientlib/styles/rte-style.css";

    @Inject
    private ResourceResolver resourceResolver;

    @Self
    private Resource resource;

    @PlaceOnTab(WarriorDescriptionComponent.TAB_MAIN)
    @RichTextEditor(
            features = {
                    RteFeatures.UNDO_UNDO,
                    RteFeatures.UNDO_REDO,
                    RteFeatures.SEPARATOR,
                    RteFeatures.Popovers.EDIT_ALL,
                    RteFeatures.SEPARATOR,
                    RteFeatures.Popovers.FORMAT_ALL,
                    RteFeatures.Popovers.STYLES,
                    RteFeatures.Popovers.JUSTIFY_ALL,
                    RteFeatures.SEPARATOR,
                    RteFeatures.MISCTOOLS_SPECIALCHARS,
                    RteFeatures.SEPARATOR,
                    RteFeatures.Popovers.PARAFORMAT
            },
            htmlPasteRules = @HtmlPasteRules(
                    allowImages = false,
                    allowLists = AllowElement.DISALLOW,
                    allowTables = AllowElement.DISALLOW
            ),
            externalStyleSheets = {WarriorDescriptionComponent.EXTERNAL_STYLE_PATH},
            styles = {
                    @Style(cssName = "rte-style", text = "Aggressive style"),
            },
            icons = {
                    @IconMapping(command = "#styles", icon = "textColor"),
                    @IconMapping(command = "#edit", icon = "textEdit")
            },
            htmlLinkRules = @HtmlLinkRules(
                    targetInternal = LinkTarget.MANUAL,
                    targetExternal = LinkTarget.BLANK,
                    protocols = {"http://", "https://"},
                    defaultProtocol = "https://"
            ),
            specialCharacters = {
                    @Characters(name = "Copyright", entity = "&copy"),
                    @Characters(name = "Euro sign", entity = "&#x20AC"),
                    @Characters(name = "Trademark", entity = "&#x2122"),
                    @Characters(rangeStart = 48, rangeEnd = 70),
            },
            formats = {
                    @ParagraphFormat(tag = "h1", description = "My custom header"),
                    @ParagraphFormat(tag = "h2", description = "My custom subheader")
            }
    )
    @DialogField(name = "./description")
    @ValueMapValue
    private String description;

    @PlaceOnTab(WarriorDescriptionComponent.TAB_MAIN)
    @DatePicker(
            type = DatePickerType.DATE,
            displayedFormat = "DD.MM.YYYY",
            valueFormat = "DD.MM.YYYY",
            maxDate = @DateTimeValue(day = 1, month = 12, year = 2019),
            typeHint = TypeHint.STRING
    )
    @DialogField(
            name = "./birthday",
            label = "Birthday",
            description = "Enter a birthday of you warrior"
    )
    @ValueMapValue
    private String birthday;

    @DependsOnRef(name = "isLikeFruits")
    @PlaceOnTab(WarriorDescriptionComponent.TAB_TASTES)
    @DialogField(
            name = "./isLikeFruits",
            label = "Does your warrior like fruits?"
    )
    @Checkbox
    @ValueMapValue
    private boolean isLikeFruits;

    @PlaceOnTab(WarriorDescriptionComponent.TAB_FRUITS)
    @Autocomplete(
            multiple = true,
            forceSelection = true,
            datasource = @AutocompleteDatasource(namespaces = {"fruits"})
    )
    @DialogField(
            name = "./fruits",
            label = "Favorite fruits"
    )
    @ValueMapValue
    private String[] fruits;

    @PlaceOnTab(WarriorDescriptionComponent.TAB_MAIN)
    @RadioGroup(buttons = {
            @RadioButton(text = "Funny", value = "always creepy smiling", checked = true),
            @RadioButton(text ="Sad", value = "always steal your handkerchief"),
            @RadioButton(text = "Angry", value = "always ignore you")
    })
    @DialogField(
            name = "./character",
            label = "Character",
            description = "Choose character of your warrior",
            ranking = 4
    )
    @ValueMapValue
    private String character;

    @DependsOnRef(name = "isLikeFilms")
    @PlaceOnTab(WarriorDescriptionComponent.TAB_TASTES)
    @Checkbox
    @DialogField(
            name = "./isLikeFilms",
            label = "Does your warrior like films?"
    )
    @ValueMapValue
    private boolean isLikeFilms;

    @PlaceOnTab(WarriorDescriptionComponent.TAB_FILMS)
    @DependsOn(query = "@parentPath", action = "namespaceFilter")
    @Autocomplete(
            multiple = true,
            forceSelection = true,
            datasource = @AutocompleteDatasource(namespaces = {"films"})
    )
    @DialogField(
            label = "Favorite films",
            name = "./films"
    )
    @ValueMapValue
    private String[] films;

    @DependsOnRef(name = "parentPath")
    @Hidden
    @DialogField
    @ValueMapValue
    private String parentPath;

    @PostConstruct
    private void getParentPath() throws PersistenceException {
        ModifiableValueMap valueMap = resource.adaptTo(ModifiableValueMap.class);
        valueMap.put("parentPath", resource.getParent().getParent().getPath());
        resource.getResourceResolver().commit();
    }

    public String getWarriorName() {
        String warriorName = null;

        Resource resource = resourceResolver.getResource(PathConstants.WARRIOR_COMPONENT_PATH);

        if (resource != null) {
            warriorName = resource.getValueMap().get("warriorName", String.class);
        }

        return (warriorName != null ? warriorName : "The Guy");
    }

    public String getDescription() { return description; }

    public String getBirthday() {

        if (birthday != null) {
            return birthday;
        }
        return "29.03.2000";
    }

    public String getFilms() {
        if (isLikeFilms && films != null) {
            return ("his favorite film genres: " + String.join(", ",
                    Arrays.stream(films).
                            map(film -> film.replaceAll("^.*/(.*)$", "$1")).
                             toArray(String[]::new)));
        }
        return "he doesn't like films";
    }

    public String getFruits() {
        if (isLikeFruits && fruits != null) {
            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            return ("His favorite fruits: " + String.join(", ",
                    Arrays.stream(fruits).
                            map(fruit -> tagManager.resolve(fruit).getTitle()).
                            toArray(String[]::new)));
        }
        return "He doesn't like fruits";
    }

    public String getInitDescription() {
        return
                String.format("%s was born on the cold but bright day %s. He is %s. %s, and %s",
                        getWarriorName(), getBirthday(), getCharacter(), getFruits(), getFilms());
    }

    public String getCharacter() {
        if (character != null) {
            return character;
        }
        return "always creepy smiling";
    }
}
