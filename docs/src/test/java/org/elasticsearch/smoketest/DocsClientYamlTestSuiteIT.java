/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.smoketest;

import com.carrotsearch.randomizedtesting.annotations.Name;
import com.carrotsearch.randomizedtesting.annotations.ParametersFactory;
import org.elasticsearch.Version;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.http.HttpHost;
import org.elasticsearch.test.rest.yaml.ClientYamlDocsTestClient;
import org.elasticsearch.test.rest.yaml.ClientYamlTestCandidate;
import org.elasticsearch.test.rest.yaml.ClientYamlTestClient;
import org.elasticsearch.test.rest.yaml.ESClientYamlSuiteTestCase;
import org.elasticsearch.test.rest.yaml.restspec.ClientYamlSuiteRestSpec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DocsClientYamlTestSuiteIT extends ESClientYamlSuiteTestCase {

    public DocsClientYamlTestSuiteIT(@Name("yaml") ClientYamlTestCandidate testCandidate) {
        super(testCandidate);
    }

    @ParametersFactory
    public static Iterable<Object[]> parameters() throws Exception {
        return ESClientYamlSuiteTestCase.createParameters();
    }

    @Override
    protected void afterIfFailed(List<Throwable> errors) {
        super.afterIfFailed(errors);
        String name = getTestName().split("=")[1];
        name = name.substring(0, name.length() - 1);
        name = name.replaceAll("/([^/]+)$", ".asciidoc:$1");
        logger.error("This failing test was generated by documentation starting at {}. It may include many snippets. "
                + "See docs/README.asciidoc for an explanation of test generation.", name);
    }

    @Override
    protected boolean randomizeContentType() {
        return false;
    }

    @Override
    protected ClientYamlTestClient initClientYamlTestClient(ClientYamlSuiteRestSpec restSpec, RestClient restClient, List<HttpHost> hosts,
                                                            Version esVersion, Map<HttpHost, Version> hostVersionMap) throws IOException {
        return new ClientYamlDocsTestClient(restSpec, restClient, hosts, esVersion, hostVersionMap);
    }
}
