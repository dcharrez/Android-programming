# Web Services


### Examples of API in JSON from github

* https://api.github.com/users/dcharrez
* https://api.github.com/users/dcharrez/repos
* https://api.github.com/users/dcharrez/followers


### Example to retrieve followers of a github user using volley

1. Add volley library
![](https://)
2. Add permission in the manifest
![](https://)
3. Add a button to retrieve followers
```
    <Button
        android:text="Get Followers"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btn_get_followers"
        android:onClick="getFollowerClicked" />
```
4. Function that is called on click
```
    public void getFollowerClicked(View view) {

        clearRepoList();
        getFollowerList(etGitHubUser.getText().toString());
    }
```
5. Function that makes the call with the given user
```
    private void getFollowerList(String username) {
        this.url = this.baseUrl + username + "/followers";
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
//                                    Log.e("Volley", jsonObj.toString());
                                    String followerName = jsonObj.get("login").toString();
                                    String lastUpdated = jsonObj.get("avatar_url").toString();
                                    addToRepoList(followerName, lastUpdated);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            setRepoListText("No repos found.");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }
```