Parse.Cloud.afterSave("SushiUpdates", function(request) {
  var objectName = ""+request.object.get("objectName");
  console.log(" h " + request.object.updatedAt);
  console.log("afterSave " + objectName);

  Parse.Cloud.useMasterKey();

  //clean old entries
  var query = new Parse.Query("SushiUpdates");
  query.equalTo("objectName", objectName);
  query.notEqualTo("objectId", request.object.id);
  query.find({
    success: function(results) {
      for (var i = 0; i < results.length; i++) {
        results[i].destroy();
      }
    }
  });

  //send push to clients
  var pushQuery = new Parse.Query(Parse.Installation);
  pushQuery.equalTo("channels", "Sushi");
  pushQuery.equalTo("sushi", objectName);
  Parse.Push.send({
    where: pushQuery,
    data: {
      class: objectName
    }
  }, {
    success: function() {
      console.log("push sent for class " + objectName);
    },
    error: function(error) {
      console.error("push not sent for class " + objectName + " : " + error.message);
    }
  });

});
