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

package com.exadel.aem.toolkit.plugin.util.writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exadel.aem.toolkit.plugin.util.TestConstants;

public class TestXmlUtility {
    private static final Logger LOG = LoggerFactory.getLogger(TestXmlUtility.class);

    private static final String PROJECT_NAME = "test-package";

    private TestXmlUtility() {
    }


    public static boolean doTest(FileSystem fileSystem, String className, Path pathToExpectedFiles) throws ClassNotFoundException, IOException {

        Class<?> testable = Class.forName(className);

        PackageWriter.forFileSystem(fileSystem, PROJECT_NAME, StringUtils.EMPTY).write(testable);

        Map<String, String> actualFiles = getFiles(fileSystem.getPath(TestConstants.DEFAULT_COMPONENT_NAME));
        Map<String, String> expectedFiles = getFiles(pathToExpectedFiles);

        for (String fileName : actualFiles.keySet()) {
            Path filePath = fileSystem.getPath(TestConstants.DEFAULT_COMPONENT_NAME, fileName);
            Files.delete(filePath);
        }

        return compare(actualFiles, expectedFiles, pathToExpectedFiles.toString());
    }



/*
    private static Map<String, String> getActualFiles(Class<?> componentClass, List<PackageEntryWriter> writers) {
        Map<String, String> actualFiles = new HashMap<>();
        List<Class<?>> views = new LinkedList<>(Collections.singletonList(componentClass));
        Optional.ofNullable(componentClass.getAnnotation(Component.class)).ifPresent(component -> Collections.addAll(views, component.views()));
        writers.forEach(packageEntryWriter -> {
            try (StringWriter stringWriter = new StringWriter()) {
                if (packageEntryWriter instanceof ContentXmlWriter) {
                    writeContent(packageEntryWriter, stringWriter, views);
                    actualFiles.put(packageEntryWriter.getXmlScope().toString(), stringWriter.toString());
                    return;
                }
                List<Class<?>> processedClasses = views.stream().filter(packageEntryWriter::canProcess).collect(Collectors.toList());
                if (processedClasses.size() > 1) {
                    throw new IOException();
                }
                if (processedClasses.isEmpty()) {
                    return;
                }
                packageEntryWriter.writeXml(processedClasses.get(0), stringWriter);
                actualFiles.put(packageEntryWriter.getXmlScope().toString(), stringWriter.toString());
            } catch (PluginException pe) {
                // Deliberately re-throwing to distinguish from an unanticipated exception (needed for exceptions testcases)
                throw pe;
            } catch (Exception e) {
                LOG.error("Could not implement test writer", e);
            }
        });
        return actualFiles;
    }

    private static void writeContent(PackageEntryWriter writer, StringWriter stringWriter, List<Class<?>> views) throws Exception {
        List<Class<?>> processedClasses = views.stream().filter(writer::canProcess).collect(Collectors.toList());
        if (processedClasses.size() > 2) {
            throw new IOException();
        }
        if (processedClasses.size() == 2) {
            if (processedClasses.get(0).getDeclaredAnnotation(Component.class) != null) {
                writer.writeXml(processedClasses.get(0), stringWriter);
            } else {
                writer.writeXml(processedClasses.get(1), stringWriter);
            }
            return;
        }
        if (!processedClasses.isEmpty()) {
            writer.writeXml(processedClasses.get(0), stringWriter);
        }
    }
*/

    private static Map<String, String> getFiles(Path componentPath) {
        Map<String, String> files = new HashMap<>();
        try {
            for (Path filePath : Files.list(componentPath).collect(Collectors.toList())) {
                files.put(filePath.getFileName().toString(), String.join("", Files.readAllLines(filePath)));
            }
        } catch (NullPointerException | IOException ex) {
            LOG.error("Could not read the package " + componentPath, ex);
        }
        return files;
    }

    private static boolean compare(Map<String, String> actualFiles, Map<String, String> expectedFiles, String resourcePath) {
        if (!filesetsAreSame(actualFiles, expectedFiles)) {
            return false;
        }
        Collection<String> fileNames = expectedFiles.keySet();
        for (String fileName : fileNames) {
            String actualContent = actualFiles.get(fileName);
            String expectedContent = expectedFiles.get(fileName);
            boolean isMatch;
            try {
                isMatch = FilesComparator.compareXml(
                    actualContent,
                    expectedContent,
                    resourcePath + File.separator + fileName);
            } catch (Exception ex) {
                LOG.error("Could not implement XML files comparison", ex);
                isMatch = false;
            }
            if (!isMatch) {
                return false;
            }
        }
        return true;
    }

    private static boolean filesetsAreSame(Map<String, String> first, Map<String, String> second) {
        if (first == null || second == null || first.size() != second.size()) {
            return false;
        }
        return first.keySet().stream().allMatch(second::containsKey);
    }
}
