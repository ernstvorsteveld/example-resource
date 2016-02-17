String basePath = 'example-resource jobs'
String commitStage = "$basePath/commit stage"

folder(basePath) {
    description 'The example resource build pipeline.'
}

folder(commitStage) {
    description 'The commit stage of the build pipeline. Assertion on technical level.'
}

freeStyleJob("$commitStage/100-clean and compile") {
    description('Clone from git, clean and compile the project')
    customWorkspace('/var/jenkins_home/jobdsl')
    scm {
        git {
            remote {
                github('ernstvorsteveld/example-resource')
            }
            branch('master')
            createTag(false)
        }
    }
    triggers {
        scm('H/15 * * * *')
    }
    steps {
        gradle {
            description 'Clean up and compile.'
            useWrapper true
            tasks 'clean classes'
        }
        downstreamParameterized {
            trigger("200-test") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/200-test") {
    description('Execute the unit tests.')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        gradle {
            description 'Test the project'
            useWrapper true
            tasks 'test -x karmaRun'
        }
        downstreamParameterized {
            trigger("300-integration tests") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/300-integration tests") {
    description('Execute the integration tests')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        gradle {
            description 'Test the project'
            useWrapper true
            tasks 'integrationTest'
        }
        downstreamParameterized {
            trigger("javascript tests") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/400-javascript tests") {
    description('Execute the javascript tests')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        gradle {
            description 'Test the javascript code'
            useWrapper true
            tasks 'karmaRun javascriptTest'
        }
        downstreamParameterized {
            trigger("500-code analysis") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/500-code analysis") {
    description('Execute the code analysis')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        downstreamParameterized {
            trigger("600-create jar") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/600-create jar") {
    description('Create the jar')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        gradle {
            description 'Create the jar.'
            useWrapper true
            tasks 'jar'
        }
        downstreamParameterized {
            trigger("700-deploy nexus") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/700-deploy nexus") {
    description('Deploy to nexus')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        downstreamParameterized {
            trigger("800-export sonar") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/800-export sonar") {
    description('Reports to sonar')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        downstreamParameterized {
            trigger("900-create docker image") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/900-create docker image") {
    description('Create docker image')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
    }
}

