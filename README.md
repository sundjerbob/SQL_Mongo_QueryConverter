<h1>Database Project - MySQL to MongoDB Query Translation</h1>
<p>This project is developed by Mina Kovacevic and Tadija Simic. It aims to provide a tool for parsing MySQL statements, translating them to MongoDB queries, and utilizing JDBC to send the translated queries to MongoDB.</p>
<h2>Getting Started</h2>
<p>To use this projec`t, follow the instructions below:</p>
<ol>
  <li>Clone the repository to your local machine:</li>
  <pre><code>git clone https://github.com/mina-tadija/database-project.git</code></pre>
  <li>Set up your MySQL and MongoDB databases:</li>
  <ul>
    <li>Install MySQL and create your MySQL database.</li>
    <li>Install MongoDB and create your MongoDB database.</li>
  </ul>
  <li>Configure the project:</li>
  <ul>
    <li>Open the project in your preferred Java IDE.</li>
    <li>Update the database connection details in the <code>config.properties</code> file to match your MySQL and MongoDB configurations.</li>
  </ul>
  <li>Build and run the project:</li>
  <ul>
    <li>Build the project using your Java IDE or using the provided build script.</li>
    <li>Run the project and access it through the provided user interface.</li>
  </ul>
  <li>Enter your MySQL statements:</li>
  <ul>
    <li>Enter your MySQL statements in the provided input field.</li>
  </ul>
  <li>View the translated MongoDB queries:</li>
  <ul>
    <li>Query value in input field is automatically translated if possible.</li>
    <li>When the query in input field become parsable UI elements will change colour signifying that query could be executed</li>
    <li>The translated MongoDB queries will be displayed in the tran field.</li>
  </ul>
  <li>Send queries to MongoDB:</li>
  <ul>
    <li>Click the "Execute" button send a translated query statement to your MongoDB database.</li>
    <li>Result set of the executed query will be displayed via GUI in data table representation.</li>
  </ul>
</ol>
<h2>Contributing</h2>
<p>Contributions to this project are welcome. If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.</p>
<h2>License</h2>
<p>This project is licensed under the team_MK_TS License. See the <code>LICENSE</code> file for more details.</p>
