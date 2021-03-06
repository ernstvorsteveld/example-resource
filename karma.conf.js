// Karma configuration
// Generated on Mon Dec 07 2015 22:03:32 GMT+0100 (CET)

module.exports = function (config) {
    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: '',


        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine'],


        // list of files / patterns to load in the browser
        files: [
            'src/main/resources/static/components/angular/angular.js',
            'src/main/resources/static/components/angular-route/angular-route.js',
            'src/main/resources/static/components/angular-resource/angular-resource.js',
            'src/main/resources/static/components/angular-mocks/angular-mocks.js',
            'src/main/resources/static/app/**/*.js',
            'src/test/resources/static/spec/**/*.js'
        ],


        // list of files to exclude
        exclude: [],

        //plugins: ['karma-chrome-launcher',
        //    'karma-jasmine',
        //    'ng-html2js',
        //    'karma-ng-html2js-preprocessor'],
        plugins: [
                'karma-jasmine',
                'karma-coverage',
                'karma-phantomjs-launcher'
                  //'karma-chrome-launcher',
                  //'karma-safari-launcher'
        ],

        // pre-process matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {
          'src/main/resources/static/app/**/*.js': 'coverage'
          //'src/app/view/**/*.html': 'ng-html2js'
        },

        ngHtml2JsPreprocessor: {
            //stripPrefix: 'src/'
        },


        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress', 'coverage'],

        coverageReporter: {
            type : 'html',
            dir : 'build/coverage/'
        },

        // web server port
        port: 9876,


        // enable / disable colors in the output (reporters and logs)
        colors: true,


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,


        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,


        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['PhantomJS'],


        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: false
    });
};
