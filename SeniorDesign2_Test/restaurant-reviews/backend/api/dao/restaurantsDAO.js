// Variable to store a reference to our database
let restaurants

export default class RestaurantsDAO {
  // First async method. Connects to the database.
  static async injectDB(conn) {
    // Return if the variable restaurants is already filled
    if (restaurants) {
      return
    }
    
    // Otherwise, try to connect to our database and get the collection restaurants
    // The collection restaurants is inside the sample_restaurants DB
    try {
      restaurants = await conn.db(process.env.RESTREVIEWS_NS).collection ("restaurants")
    } catch (e) {
      console.error(
        `Unable to establish a collection handle in restaurantsDAO: ${e}`,
      )
    }
  }

  // 2nd Async method. Gets all the restaurants in the DB using queries.
  static async getRestaurants({
    filters = null,
    page = 0,
    restaurantsPerPage = 20,
  } = {}) {
    let query
    if (filters) {
      if ("name" in filters) {
        query = { $text: { $search: filters["name"] } }
      } else if ("cuisine" in filters) {
        query = { "cuisine": { $eq: filters["cuisine"] } }
      } else if ("zipcode" in filters) {
        query = { "address.zipcode": { $eq: filters["zipcode"] } }
      }
    }

}