/*
 * Copyright 2011 Jin Kwon <jinahya at gmail.com>.
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


package com.github.jinahya.sql.database.metadata.bind;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
@XmlRootElement
@XmlType(
    propOrder = {
        "tableSchem",
        //"functionColumns",
        "functions",
        //"procedureColumns",
        "procedures",
        //"tablePrivileges",
        "tables", "userDefinedTypes"
    }
)
public class Schema {


    // ------------------------------------------------------------ tableCatalog
    public String getTableCatalog() {

        return tableCatalog;
    }


    public void setTableCatalog(final String tableCatalog) {

        this.tableCatalog = tableCatalog;
    }


    // -------------------------------------------------------------- tableSchem
    /**
     * Return the name of this schema.
     *
     * @return the name
     */
    public String getTableSchem() {

        return tableSchem;
    }


    /**
     * Replaces the name of this schema.
     *
     * @param tableSchem the name
     */
    public void setTableSchem(final String tableSchem) {

        this.tableSchem = tableSchem;
    }


    // ----------------------------------------------------------------- catalog
    public Catalog getCatalog() {

        return catalog;
    }


    public void setCatalog(final Catalog catalog) {

        this.catalog = catalog;
    }


    // --------------------------------------------------------------- functions
    public List<Function> getFunctions() {

        if (functions == null) {
            functions = new ArrayList<Function>();
        }

        return functions;
    }


//    // -------------------------------------------------------- procedureColumns
//    public List<ProcedureColumn> getProcedureColumns() {
//
//        if (procedureColumns == null) {
//            procedureColumns = new ArrayList<ProcedureColumn>();
//        }
//
//        return procedureColumns;
//    }
    // -------------------------------------------------------------- procedures
    public List<Procedure> getProcedures() {

        if (procedures == null) {
            procedures = new ArrayList<Procedure>();
        }

        return procedures;
    }


    // ------------------------------------------------------------------ tables
    /**
     * Returns a list of tables.
     *
     * @return tables
     */
    public List<Table> getTables() {

        if (tables == null) {
            tables = new ArrayList<Table>();
        }

        return tables;
    }


    public List<String> getTableNames() {

        final List<String> tableNames
            = new ArrayList<String>(getTables().size());

        for (final Table table : getTables()) {
            tableNames.add(table.getTableName());
        }

        return tableNames;
    }


    public Table getTableByName(final String tableName) {

        if (tableName == null) {
            throw new NullPointerException("null tableName");
        }

        for (final Table table : getTables()) {
            if (tableName.equals(table.getTableName())) {
                return table;
            }
        }

        return null;
    }


//    // --------------------------------------------------------- tablePrivileges
//    public List<TablePrivilege> getTablePrivileges() {
//
//        if (tablePrivileges == null) {
//            tablePrivileges = new ArrayList<TablePrivilege>();
//        }
//
//        return tablePrivileges;
//    }
    // -------------------------------------------------------- userDefinedTypes
    public List<UserDefinedType> getUserDefinedTypes() {

        if (userDefinedTypes == null) {
            userDefinedTypes = new ArrayList<UserDefinedType>();
        }

        return userDefinedTypes;
    }


    /**
     * catalog name (may be {@code null}).
     */
    @Label("TABLE_CATALOG")
    @XmlAttribute(required = false)
    private String tableCatalog;


    @Label("TABLE_SCHEM")
    @XmlElement(nillable = true, required = true)
    private String tableSchem;


    @XmlTransient
    private Catalog catalog;


//    @Invocation(
//        name = "getFunctionColumns",
//        types = {String.class, String.class, String.class, String.class},
//        argsarr = {
//            @InvocationArgs({":tableCatalog", ":tableSchem", "null", "null"})
//        }
//    )
//    @XmlElementRef
//    private List<FunctionColumn> functionColumns;
    @Invocation(
        name = "getFunctions",
        types = {String.class, String.class, String.class},
        argsarr = {
            @InvocationArgs({":tableCatalog", ":tableSchem", "null"})
        }
    )
    @XmlElementRef
    private List<Function> functions;


//    @Invocation(
//        name = "getProcedureColumns",
//        types = {String.class, String.class, String.class, String.class},
//        argsarr = {
//            @InvocationArgs({":tableCatalog", ":tableSchem", "null", "null"})
//        }
//    )
//    @XmlElementRef
//    private List<ProcedureColumn> procedureColumns;
    @Invocation(
        name = "getProcedures",
        types = {String.class, String.class, String.class},
        argsarr = {
            @InvocationArgs({":tableCatalog", ":tableSchem", "null"})
        }
    )
    @XmlElementRef
    private List<Procedure> procedures;


    @Invocation(
        name = "getTables",
        types = {String.class, String.class, String.class, String[].class},
        argsarr = {
            @InvocationArgs({":tableCatalog", ":tableSchem", "null", "null"})
        }
    )
    @XmlElementRef
    private List<Table> tables;


//    @Invocation(
//        name = "getTablePrivileges",
//        types = {String.class, String.class, String.class},
//        argsarr = {
//            @InvocationArgs({":tableCatalog", ":tableSchem", "null"})
//        }
//    )
//    @XmlElementRef
//    private List<TablePrivilege> tablePrivileges;
    @Invocation(
        name = "getUDTs",
        types = {String.class, String.class, String.class, int[].class},
        argsarr = {
            @InvocationArgs({":tableCatalog", ":tableSchem", "null", "null"})
        }
    )
    @XmlElementRef
    private List<UserDefinedType> userDefinedTypes;


}
