# Assumptions

<ul style="list-style-type:disc">
  <li>The system assumes there are two types of users: <strong>Admin</strong> and <strong>User</strong>.</li>
  <ul style="list-style-type:circle">
    <li><strong>Admin</strong>: Can perform all CRUD operations (Create, Read, Update, Delete) on resources.</li>
    <li><strong>User</strong>: Has read-only access to the resources.</li>
  </ul>

  <li>When a specified author is deleted:</li>
  <ul style="list-style-type:circle">
    <li>All related documents are also deleted within the Document Service.</li>
    <li>An event is sent to a Kafka queue.</li>
    <li>The Notification Service, acting as a consumer, reads this event from the Kafka queue and logs the deletion in the database.</li>
  </ul>

  <li>When an author is created, an event is published to the Kafka queue.</li>
  <ul style="list-style-type:circle">
    <li>Similar events can be published for other CRUD operations (Create, Update, Delete).</li>
    <li>The Notification Service handles these events and logs them accordingly in the database.</li>
  </ul>
</ul>
