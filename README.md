# Melviz Core
[![Build Melviz and Upload Artifacts](https://github.com/OpenMelViz/melviz-core/actions/workflows/maven.yml/badge.svg)](https://github.com/OpenMelViz/melviz-core/actions/workflows/maven.yml)

Melviz is a tool to visualize data visualizations, dashboards and reports built using YAML.

* Supports YAML based pages, allowing users to build dahsboards and reports in a declarative way;
* Can read data from JSON, metrics and CSV sources;
* Data can be transformed using JSONAta;
* Support microfrontends for custom visualizations;
* Can pull real-time data from its datasets;
* Allow Communication between components using Filter components;

This is the core project. It results in a web application that can be embed or live standalone to render YAML contents.

Licensed under the Apache License, Version 2.0

For further information, please visit the project web site <a href="http://melviz.org" target="_blank">melviz.org</a>

## Requirements

* Java 21
* Maven

## Building

Run `mvn clean install` on project root and find the web application in directory `melviz-webapp-parent/melviz-webapp/target/melviz-webapp`. 

The web application can receive dynamic content by posting YAML to the main frame. Here's a sample Javascript code:

```
window.postMessage(`pages:    
  - components:
    - markdown: "# Hello World!"
`, null)
```

It is also possible to use `setup.js` to configure static dashboards.

Melviz can run in any web server or in Github Pages.



