/**
 * Copyright 2018 SourceLab.org https://github.com/SourceLabOrg/kafka-connect-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;

/**
 * Look for LoggerFactory.getLogger(classname) where classname doesn't match the class its part of.
 * This is kind of a half assed way to do this.
 */
public class BadLoggersTest {
    private static final Logger logger = LoggerFactory.getLogger(BadLoggersTest.class);

    // Weak attempt
    private static final Pattern regexPattern = Pattern.compile("LoggerFactory.getLogger\\((.*)\\.class\\)");

    @Test
    public void doTest() throws FileNotFoundException {
        // Hacky way to determine root path
        final File currentPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        final File projectRootPath = currentPath.getParentFile().getParentFile();
        logger.info("Root Path: {}", projectRootPath);

        // Walk all the files in the path
        walk(projectRootPath);
    }

    private void walk(File root) throws FileNotFoundException {
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f);
            } else {
                // Skip non java source files
                if (!f.getAbsoluteFile().getPath().endsWith(".java")) {
                    continue;
                }
                testFile(f);
            }
        }
    }

    private void testFile(File myFile) throws FileNotFoundException {
        String fileData = new Scanner(myFile).useDelimiter("\\Z").next();

        // Look for our pattern
        Matcher matches = regexPattern.matcher(fileData);

        // If we didn't find a match
        if (!matches.find()) {
            return;
        }

        // Grab out the Class name
        String loggerClassName = matches.group(1);
        if (loggerClassName == null) {
            return;
        }

        // Get class name from the file name
        // I bet this will be completely broken for inner classes...
        // if you run into that, just exclude it? or figure out a better solution to this :p
        String className = myFile.getName().replace(".java", "");
        if (!className.equals(loggerClassName)) {
            logger.info("Class {} ClassNameUsedByLogger {} ", className, loggerClassName);
            assertFalse("Found instance of logger using wrong class? " + myFile.getPath() + " Using " + loggerClassName, true);
        }
    }
}
