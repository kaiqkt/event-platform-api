package com.kaiqkt.eventplatform.domain.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageRequestBuilder {
    /**
     * Default page number.
     */
    private static final Integer DEFAULT_PAGE = 0;

    /**
     * Default page size.
     */
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * Default sort properties.
     */
    /**
     * Returns a PageRequest object with the given parameters. If a parameter is null, a default value is used.
     *
     * @param page         the page number
     * @param size         the page size
     * @param direction    the sort direction
     * @param sortProperty the property to sort by
     * @return a PageRequest object
     */
    public static PageRequest build(
            Integer page,
            Integer size,
            String direction,
            String sortProperty,
            List<String> DEFAULT_SORT_PROPERTIES
    ) {
        if (sortProperty != null && !DEFAULT_SORT_PROPERTIES.contains(sortProperty)) {
            throw new IllegalArgumentException(String.format("Invalid sort property %s", DEFAULT_SORT_PROPERTIES));
        }

        return org.springframework.data.domain.PageRequest.of(
                page != null ? page : DEFAULT_PAGE,
                size != null ? size : DEFAULT_PAGE_SIZE,
                direction != null ? Sort.Direction.valueOf(direction) : Sort.Direction.ASC,
                sortProperty != null ? sortProperty : DEFAULT_SORT_PROPERTIES.get(0)
        );
    }
}
