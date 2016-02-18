String basePath = 'example-resource jobs'
String acceptanceStage = "$basePath/acceptance stage"

folder(basePath) {
    description 'Example build pipeline.'
}

folder(acceptanceStage) {
    description 'The acceptance stage of the build pipeline.'
}

job("$acceptanceStage/100-initialize machines") {
    description('Initialize machines')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        downstreamParameterized {
            trigger("200-deploy docker images") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$acceptanceStage/200-deploy docker images") {
    description('Deploy docker images')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        downstreamParameterized {
            trigger("300-end-to-end-tests") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$acceptanceStage/300-end-to-end-tests") {
    description('Execute end-to-end tests')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
    }
}
