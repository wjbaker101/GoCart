package com.wjbaker.gocart.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ProductSearchResponse {

    private UK uk;

    public ProductSearchResponse() {}

    public static class UK {

        private GHS ghs;

        public UK() {}

        public static class GHS {

            private Products products;

            public GHS() {}

            public static class Products {

                @SerializedName("input_query")
                private String inputQuery;

                @SerializedName("output_query")
                private String outputQuery;

                private Map<String, Object> filters;
                private String queryPhase;
                private Map<String, Integer> totals;
                private String config;
                private List<TescoProduct> results;
                private List<Map<String, String>> suggestions;

                public Products() {}

                public String getInputQuery() {
                    return inputQuery;
                }

                public void setInputQuery(String inputQuery) {
                    this.inputQuery = inputQuery;
                }

                public String getOutputQuery() {
                    return outputQuery;
                }

                public void setOutputQuery(String outputQuery) {
                    this.outputQuery = outputQuery;
                }

                public Map<String, Object> getFilters() {
                    return filters;
                }

                public void setFilters(Map<String, Object> filters) {
                    this.filters = filters;
                }

                public String getQueryPhase() {
                    return queryPhase;
                }

                public void setQueryPhase(String queryPhase) {
                    this.queryPhase = queryPhase;
                }

                public Map<String, Integer> getTotals() {
                    return totals;
                }

                public void setTotals(Map<String, Integer> totals) {
                    this.totals = totals;
                }

                public String getConfig() {
                    return config;
                }

                public void setConfig(String config) {
                    this.config = config;
                }

                public List<TescoProduct> getResults() {
                    return results;
                }

                public void setResults(List<TescoProduct> results) {
                    this.results = results;
                }

                public List<Map<String, String>> getSuggestions() {
                    return suggestions;
                }

                public void setSuggestions(List<Map<String, String>> suggestions) {
                    this.suggestions = suggestions;
                }
            }

            public Products getProducts() {
                return products;
            }

            public void setProducts(Products products) {
                this.products = products;
            }
        }

        public GHS getGhs() {
            return ghs;
        }

        public void setGhs(GHS ghs) {
            this.ghs = ghs;
        }
    }

    public UK getUk() {
        return uk;
    }

    public void setUk(UK uk) {
        this.uk = uk;
    }
}
