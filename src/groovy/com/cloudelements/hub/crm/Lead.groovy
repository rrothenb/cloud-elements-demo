package com.cloudelements.hub.crm

/*
There has to be a static method to get a collection of Leads
The groovy API should map closely to the REST API.  Pretty much a method for each URL
Should avoid duplicating structure as can't be trusted anyway, due to transformations
Static method to set Authorization
Need CEQL objects so can have type safe way to specify queries.  Maybe a DSL
 */
class Lead {
    /*
    Need to start with GET /leads.  Need to provide Authorization, where, includeDeleted, page and pageSize and should
    support optional parameters.  Need to also handle HTTP status codes and errors returned by network code.  Should
    naturally be able to access the fields in the returned objects.

    How do you tell how many would be returned?  Probably have to assume that if you got pagesize back, there could be more

    Seems like the default would be to get the Authorization from the config.  Should be able to override the location.
    Should be able to set explicitly.

    The method should be called what?  Might be good if all classes have the same name for each operation and that all
    that changes is the URL base. Consider using gorm as inspiration.
     */
}
