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


freeStyleJob() {
    description('clean and compile')
    scm {
        git 'https://github.com/ernstvorsteveld/example-resource.git'
    }
    steps {
        gradle {
            description 'Clean up and compile.'
            useWrapper true
            tasks 'clean classes'
        }
    }
}

job("$basePath/test") {
    description('Execute the unit tests.')
    steps {
        gradle {
            description 'Test the project'
            useWrapper true
            tasks 'test'
        }
    }
}

job("$basePath/integration tests") {
    description('Execute the integration tests')
    steps {
        gradle {
            description 'Test the project'
            useWrapper true
            tasks 'test'
        }
    }
}