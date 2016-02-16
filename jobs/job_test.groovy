String basePath = 'example-resource jobs'
String commitStage = "$basePath/commit stage"
String acceptanceStage = "$basePath/acceptance stage"

folder(basePath) {
    description 'The example resource build pipeline.'
}

folder(commitStage) {
    description 'The commit stage of the build pipeline. Assertion on technical level.'
}

folder(acceptanceStage) {
    description 'The acceptance stage of the build pipeline.'
}


freeStyleJob("$commitStage/clean and compile") {
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
            trigger("test") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/test") {
    description('Execute the unit tests.')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        gradle {
            description 'Test the project'
            useWrapper true
            tasks 'test'
        }
        downstreamParameterized {
            trigger("integration tests") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/integration tests") {
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

job("$commitStage/javascript tests") {
    description('Execute the javascript tests')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        gradle {
            description 'Test the javascript code'
            useWrapper true
            tasks 'javascriptTest'
        }
        downstreamParameterized {
            trigger("code analysis") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/code analysis") {
    description('Execute the code analysis')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        downstreamParameterized {
            trigger("create jar") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/create jar") {
    description('Create the jar')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        gradle {
            description 'Create the jar.'
            useWrapper true
            tasks 'jar'
        }
        downstreamParameterized {
            trigger("deploy nexus") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/deploy nexus") {
    description('Deploy to nexus')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        downstreamParameterized {
            trigger("export sonar") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/export sonar") {
    description('Reports to sonar')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
        downstreamParameterized {
            trigger("create docker image") {
                predefinedProp("SOURCE_BUILD_NUMBER","${BUILD_NUMBER}")
            }
        }
    }
}

job("$commitStage/create docker image") {
    description('Create docker image')
    customWorkspace('/var/jenkins_home/jobdsl')
    steps {
    }
}

