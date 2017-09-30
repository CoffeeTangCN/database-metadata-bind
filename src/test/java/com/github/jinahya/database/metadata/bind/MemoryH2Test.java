/*
 * Copyright 2013 <a href="mailto:onacit@gmail.com">Jin Kwon</a>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jinahya.database.metadata.bind;

import static com.github.jinahya.database.metadata.bind.JaxbTests.store;
import static com.github.jinahya.database.metadata.bind.MetadataContext.getCatalogs;
import static java.lang.invoke.MethodHandles.lookup;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static java.sql.DriverManager.getConnection;
import java.util.List;

/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class MemoryH2Test extends MemoryTest {

    private static final Logger logger = getLogger(lookup().lookupClass());

    // -------------------------------------------------------------------------
    private static final String DRIVER_NAME = "org.h2.Driver";

    private static final Class<?> DRIVER_CLASS;

    static {
        try {
            DRIVER_CLASS = Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException cnfe) {
            throw new InstantiationError(cnfe.getMessage());
        }
    }

    private static final String CONNECTION_URL
            = "jdbc:h2:mem:test"; //;DB_CLOSE_DELAY=-1";

    // -------------------------------------------------------------------------
    @BeforeClass
    private static void beforeClass() throws SQLException {
    }

    @AfterClass
    private static void afterClass() throws SQLException {
    }

    // -------------------------------------------------------------------------
    @Test(enabled = true)
    public void marshalCategories() throws Exception {
        try (Connection connection = getConnection(CONNECTION_URL)) {
            final DatabaseMetaData metadata = connection.getMetaData();
            final MetadataContext context = new MetadataContext(metadata);
            context.suppress(
                    "column/isGeneratedcolumn",
                    //                    "clientInfoProperty/defaultValue",
                    //                    "clientInfoProperty/description",
                    //                    "clientInfoProperty/maxLen",
                    "schema/functions",
                    "table/pseudoColumns"
            );
            final List<Catalog> catalogs = getCatalogs(context, true);
            store(Catalog.class, catalogs, "memory.h2");
        }
    }
}
