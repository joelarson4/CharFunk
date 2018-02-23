module.exports = function(grunt) {
    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> <%= pkg.version %> - Copyright (c) <%= grunt.template.today("yyyy") %> by Joe Larson (http://joewlarson.com), MIT License - minified <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            build: {
                src: 'src/charFunk-1.1.3.js',
                dest: 'charFunk-1.1.3.min.js'
            }
        },
        jshint: {
            files: ['Gruntfile.js', 'src/charFunk-1.1.3.js']
        }
    });

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-uglify');

    // Default task(s).
    grunt.registerTask('default', ['jshint','uglify']);
};
