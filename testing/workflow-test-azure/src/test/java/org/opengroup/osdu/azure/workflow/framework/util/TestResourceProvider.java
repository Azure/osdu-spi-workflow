// Copyright © Microsoft Corporation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.opengroup.osdu.azure.workflow.framework.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class TestResourceProvider {
  private static final String DAGS_FOLDER = "dags";
  private static final String OPERATORS_FOLDER = "operators";

  private static Configuration templateConfiguration;

  static {
    templateConfiguration = new Configuration(Configuration.VERSION_2_3_30);
    templateConfiguration.setDefaultEncoding("UTF-8");

    ClassTemplateLoader classTemplateLoader =
        new ClassTemplateLoader(TestResourceProvider.class, "/");
    templateConfiguration.setTemplateLoader(classTemplateLoader);
  }

  public static String getDAGFileContent(String dagTemplateFileName,
                                         Map<String, Object> templateContext) throws Exception  {
    return getContent(DAGS_FOLDER + "/" + dagTemplateFileName, templateContext);
  }

  public static String getOperatorFileContent(String operatorFileName) throws Exception {
    return getContent(OPERATORS_FOLDER + "/" + operatorFileName, null);
  }

  private static String getContent(String filePath, Map<String, Object> templateContext)
      throws Exception {
    if(templateContext == null) {
      templateContext = new HashMap<>();
    }
    Template template = templateConfiguration.getTemplate(filePath);
    StringWriter outputContentWriter = new StringWriter();
    template.process(templateContext, outputContentWriter);

    return outputContentWriter.toString();
  }
}
