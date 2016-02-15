String basePath = 'example-resource jobs'

folder(basePath) {
    description 'This example shows basic folder/job creation.'
}

freeStyleJob("$basePath/clean and compile") {
    description('Clean and compile the project.')
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
    description('Test the project.')
    steps {
        gradle {
            description 'Test the project'
            useWrapper true
            tasks 'test'
        }
    }
}