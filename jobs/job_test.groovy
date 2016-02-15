String basePath = 'Example-resource'

folder(basePath) {
    description 'This example shows basic folder/job creation.'
}

freeStyleJob("$basePath/clean and compile") {
    description('Clean and compile the project.')
    steps {
        gradle {
            useWrapper true
            tasks 'clean classes'
        }
    }
}

job("$basePath/test") {
    description('Test the project.')
    steps {
        gradle {
            useWrapper true
            tasks 'test'
        }
    }
}