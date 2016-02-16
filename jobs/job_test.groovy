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
    scm {
        git {
            remote {
                github('https://github.com/ernstvorsteveld/example-resource.git')
            }
            createTag(false)
        }
    }
    steps {
        gradle {
            description 'Clean up and compile.'
            useWrapper true
            tasks 'clean classes'
        }
    }
}

job("$commitStage/test") {
    description('Execute the unit tests.')
    steps {
        gradle {
            description 'Test the project'
            useWrapper true
            tasks 'test'
        }
    }
}

job("$commitStage/integration tests") {
    description('Execute the integration tests')
    steps {
        gradle {
            description 'Test the project'
            useWrapper true
            tasks 'test'
        }
    }
}

job("$commitStage/code analysis") {
    description('Execute the code analysis')
}

job("$commitStage/create jar") {
    description('Create the jar')
    steps {
        gradle {
            description 'Create the jar.'
            useWrapper true
            tasks 'jar'
        }
    }
}

job("$commitStage/create docker image") {
    description('Create docker image')
}

