package com.cloudelements.hub.crm

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.GrailsNameUtils
import spock.lang.Specification
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

@TestMixin(GrailsUnitTestMixin)
class LeadSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        given:
        RestBuilder rest = new RestBuilder()
        def tokens = grailsApplication.config.cloudelements.tokens
        def elements = grailsApplication.config.cloudelements.elements
        String baseUrl = "https://api.cloud-elements.com/elements/api-v2"
        RestResponse instances = rest.get("$baseUrl/instances") {
            header "Authorization", "User ${tokens.user}, Organization ${tokens.organization}"
        }
        tokens.elements = instances.json.collectEntries {[(it.element.key):it.token]}

        when:
        RestResponse leads = rest.get("$baseUrl/hubs/crm/leads") {
            header "Authorization", "User ${tokens.user}, Organization ${tokens.organization}, Element ${tokens.elements[elements.crm]}"
        }
        RestResponse customers = rest.get("$baseUrl/hubs/ecommerce/customers") {
            header "Authorization", "User ${tokens.user}, Organization ${tokens.organization}, Element ${tokens.elements[elements.ecommerce]}"
        }
        customers.json.each { customer ->
            if (!leads.json*.email.contains(customer.email)) {
                println "${customer.email} is new!"
                RestResponse newLead = rest.post("$baseUrl/hubs/crm/leads") {
                    header "Authorization", "User ${tokens.user}, Organization ${tokens.organization}, Element ${tokens.elements[elements.crm]}"
                    json([email:customer.email, first_name:customer.first_name, last_name:customer.last_name])
                }
                println newLead.json
            }
            else {
                println "${customer.email} is old"
            }
        }

        then:
        println leads.json*.email
        println leads.json*.first_name
        println leads.json*.last_name
        println leads.json*.phone
        println leads.json*.address1
        println leads.json*.city
        println leads.json*.province_code
        println leads.json*.zip
        println customers.json*.email
        println customers.json*.first_name
        println customers.json*.last_name
        println customers.json*.default_address.phone
        println customers.json*.default_address.address1
        println customers.json*.default_address.city
        println customers.json*.default_address.province_code
        println customers.json*.default_address.zip
        println "done"
        leads.json*.first_name.contains "Jessa"
    }
}

// TODO parameterize User, Organization and Element tokens
// TODO only configure user and organization and lookup element tokens at startup
// TODO create reusable class that has an interface like gorm
// zoho token - y849Vp/21SrCjm+t//b80UufQApBJmT9G83nRHYc0UQ=