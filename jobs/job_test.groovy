String basePath = 'Example-resource'

folder(basePath) {
    description 'This example shows basic folder/job creation.'
}

freeStyleJob("$basePath/clean and compile") {
    description('Clean and compile the project.')
    steps {
        grails {
            useWrapper true
            targets(['clean', 'classes'])
        }
    }
}

job("$basePath/test") {
    description('Test the project.')
    steps {
        grails {
            useWrapper true
            targets(['test'])
        }
    }
}