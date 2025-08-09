# Module Three final project

## Design Requirements

All applications must be accompanied by project design documents, wireframes, mockups, and/or diagrams.
 * Required design documents must be submitted along with the source code in the `client/design` folder.
 * Recommended design documents are optional.

### Required

* [**Custom project only**] Documentation of functional requirements to act as the application's Minimum Viable Product ([MVP](https://en.wikipedia.org/wiki/Minimum_viable_product)).
  * A functional requirement describes what the application can do or provide from a user's perspective.
  * Functional requirements can be encapsulated into a *[user story](https://en.wikipedia.org/wiki/User_story)*. For example,
    * "As an authenticated user, I can click the "Cart" link on the navigation bar to visit my shopping cart."
    * "As an anonymous or authenticated user, on the Products page, I can view the product list as a tiled view or a table view, by clicking the appropriate icon."
* [Wireframes](https://www.figma.com/resource-library/what-is-wireframing/)
  * Show the **basic** page layout
    * Header, footer, main content area, navigation, headings, whitespace/padding
    * Indicate interactive elements
      * Buttons, links, forms, dropdown menus, pop-ups ([modal](https://www.digitalsilk.com/digital-trends/modal-in-web-design/)), switches
    * Placeholder images
    * Mock text and mock data
  * Indicate the transitions between pages
  * A separate wireframe for every page view in your application
  * A separate wireframe for mobile and desktop page views
    * Desktop view is 1024 pixel width or greater
    * Mobile view is 425 pixel width or smaller
  * Can be created by hand or digitally using an online tool like [Figma](https://www.figma.com/)
    * Here are some [examples](https://www.visual-paradigm.com/learning/handbooks/agile-handbook/wireframe.jsp) of wireframes
    * Place your wireframes in the `client/design` folder


### Recommended

* Choose a color scheme for your site
  * example: https://coolors.co/
* Choose Typography
  * example: https://fonts.google.com/
* Choose icons
  * example: https://fontawesome.com/

## Creating or Extending your own Application

If you decide to create your own application or extend upon your mid-module project, **you must confirm your application proposal with your instructor.**

### Custom Project Requirements

The following are the minimum requirements your application must include:

* Documentation of at least **7 functional requirements**
  * Requirements must reference at least two users: an anonymous user and an authenticated user
    * Anonymous user: a person who hasn't yet logged into the application
    * Authenticated user: a person who has registered with and logged into the application
  * At least 3 requirements must include an authenticated user
  * The 3 provided login, logout, and registration requirements **can not** be included as a part of the 7 functional requirements
* Must use Vue.js and associated tooling to create a front-end web application.
  * A front-end web application using only HTML, CSS, and vanilla JavaScript is **not** acceptable
* Must include at least 3 pages (views)
  * Must use Vue Router to transition between the pages (views)
  * The provided login, logout, and registration pages **can not** count towards the 3 pages
* Must implement the 3 functional requirements for user registration, login, and logout listed below
* Must consume a REST API hosted by a web server created using Spring Boot and Postgres either created by the student or provided by your instructor
  * This will be the module-2 final project unless an exception is granted by your instructor
  * The application must enable users to either create, update, or delete data in the database through the REST API
  * The application must dynamically display at least 5 JavaScript objects from an array obtained from the REST API
    * **Hard-coding data in a JavaScript array/object or in the HTML section is not permitted**
* Styling that utilizes CSS flexbox and/or CSS grid
  * You may use other CSS libraries (bootstrap, tailwind, etc.) to *supplement* your CSS styling
* No horizontal (left or right) scrolling of the page in the browser window
* Support for both desktop and mobile views
  * Desktop view is 1024 pixel width or greater
  * Mobile view is 425 pixel width or smaller
* At least 5 custom event listeners and event handlers that respond to user input
  * Event listeners for lifecycle hooks, e.g. `created` or `mounted`, can not be used as one of the 5 event listeners
* A clear purpose, function, or utility

#### Registration and login functional requirements
The following 3 functional requirements **must** be implemented for all custom projects.

* As an anonymous user, I can register a new account
  * This feature already exists—**you just need to style the registration page**
* As an anonymous user, I can login using an existing username and password
  * This feature already exists in the starter code—**you just need to style the login page**
* As an authenticated user, I can click "Logout" to logout of the application
  * After logout, the system redirects me to the Login page
  * This feature already exists in the starter code



