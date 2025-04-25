package com.example.hotel_booking_app.network;

import com.example.hotel_booking_app.models.Booking;
import com.example.hotel_booking_app.models.Carousel;
import com.example.hotel_booking_app.models.Facility;
import com.example.hotel_booking_app.models.Feature;
import com.example.hotel_booking_app.models.Query;
import com.example.hotel_booking_app.models.Review;
import com.example.hotel_booking_app.models.Room;
import com.example.hotel_booking_app.models.Settings;
import com.example.hotel_booking_app.models.User;
import com.example.hotel_booking_app.models.ManagementTeam;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // Auth
    @FormUrlEncoded
    @POST("api/v1/auth/login")
    Call<Void> login(@Field("email") String email, @Field("password") String password);

    @POST("api/v1/auth/register")
    Call<Void> registerUser(@Body User user);

    @FormUrlEncoded
    @POST("api/v1/auth/admin_login")
    Call<Void> adminLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/v1/auth/forget_password")
    Call<Void> forgetPassword(@Field("email") String email);

    // Rooms
    @GET("api/v1/rooms/get_rooms")
    Call<List<Room>> getRooms(@retrofit2.http.Query("limit") Integer limit);

    @GET("api/v1/rooms/get_room")
    Call<Room> getRoom(@retrofit2.http.Query("id") int id);

    @POST("api/v1/rooms/add_room")
    Call<Void> addRoom(@Body Room room);

    @POST("api/v1/rooms/update_room")
    Call<Void> updateRoom(@Body Room room);

    @POST("api/v1/rooms/delete_room")
    Call<Void> deleteRoom(@retrofit2.http.Query("id") int id);

    @GET("api/v1/rooms/filter_rooms")
    Call<List<Room>> filterRooms(@retrofit2.http.Query("check_in") String checkIn, @retrofit2.http.Query("check_out") String checkOut,
                                 @retrofit2.http.Query("adults") String adults, @retrofit2.http.Query("children") String children,
                                 @retrofit2.http.Query("facility") String facility);

    // Features
    @GET("api/v1/features/get_features")
    Call<List<Feature>> getFeatures();

    @POST("api/v1/features/add_feature")
    Call<Void> addFeature(@Body Feature feature);

    @POST("api/v1/features/update_feature")
    Call<Void> updateFeature(@Body Feature feature);

    @POST("api/v1/features/delete_feature")
    Call<Void> deleteFeature(@retrofit2.http.Query("id") int id);

    // Facilities
    @GET("api/v1/facilities/get_facilities")
    Call<List<Facility>> getFacilities(@retrofit2.http.Query("limit") Integer limit);

    @POST("api/v1/facilities/add_facility")
    Call<Void> addFacility(@Body Facility facility);

    @POST("api/v1/facilities/update_facility")
    Call<Void> updateFacility(@Body Facility facility);

    @POST("api/v1/facilities/delete_facility")
    Call<Void> deleteFacility(@retrofit2.http.Query("id") int id);

    // Bookings
    @GET("api/v1/bookings/get_bookings")
    Call<List<Booking>> getBookings(@retrofit2.http.Query("user_id") String userId);

    @GET("api/v1/bookings/get_new_bookings")
    Call<List<Booking>> getNewBookings();

    @POST("api/v1/bookings/add_booking")
    Call<Void> addBooking(@Body Booking booking);

    @POST("api/v1/bookings/update_booking")
    Call<Void> updateBooking(@Body Booking booking);

    @POST("api/v1/bookings/cancel_booking")
    Call<Void> cancelBooking(@retrofit2.http.Query("id") int id);

    @POST("api/v1/bookings/assign_room")
    Call<Void> assignRoom(@retrofit2.http.Query("booking_id") int bookingId, @retrofit2.http.Query("room_id") int roomId);

    // Reviews
    @GET("api/v1/reviews/get_reviews")
    Call<List<Review>> getReviews(@retrofit2.http.Query("limit") Integer limit);

    @GET("api/v1/reviews/get_reviews_by_room")
    Call<List<Review>> getReviewsByRoom(@retrofit2.http.Query("room_id") int roomId, @retrofit2.http.Query("limit") Integer limit);

    @POST("api/v1/reviews/add_review")
    Call<Void> addReview(@Body Review review);

    @POST("api/v1/reviews/mark_review_read")
    Call<Void> markReviewRead(@retrofit2.http.Query("id") int id);

    @POST("api/v1/reviews/delete_review")
    Call<Void> deleteReview(@retrofit2.http.Query("id") int id);

    // Queries
    @GET("api/v1/queries/get_queries")
    Call<List<Query>> getQueries();

    @POST("api/v1/queries/add_query")
    Call<Void> addQuery(@Body Query query);

    @POST("api/v1/queries/mark_query_read")
    Call<Void> markQueryRead(@retrofit2.http.Query("id") int id);

    @POST("api/v1/queries/delete_query")
    Call<Void> deleteQuery(@retrofit2.http.Query("id") int id);

    // Carousel
    @GET("api/v1/carousel/get_carousel")
    Call<List<Carousel>> getCarousel();

    @POST("api/v1/carousel/add_carousel")
    Call<Void> addCarousel(@retrofit2.http.Query("image_url") String imageUrl);

    @POST("api/v1/carousel/delete_carousel")
    Call<Void> deleteCarousel(@retrofit2.http.Query("id") int id);

    // Users
    @GET("api/v1/users/get_users")
    Call<List<User>> getUsers();

    @POST("api/v1/users/update_user")
    Call<Void> updateUser(@Body User user);

    @GET("api/v1/users/get_user")
    Call<User> getUser(@retrofit2.http.Query("id") String id);

    // Settings
    @GET("api/v1/settings/get_settings")
    Call<Settings> getSettings();

    @POST("api/v1/settings/update_settings")
    Call<Void> updateSettings(@Body Settings settings);

    // Management Team
    @GET("api/v1/management_team/get_management_team")
    Call<List<ManagementTeam>> getManagementTeam();

    @POST("api/v1/management_team/add_management_team")
    Call<Void> addManagementTeam(@Body ManagementTeam team);

    @POST("api/v1/management_team/update_management_team")
    Call<Void> updateManagementTeam(@Body ManagementTeam team);

    @POST("api/v1/management_team/delete_management_team")
    Call<Void> deleteManagementTeam(@retrofit2.http.Query("id") int id);

    // Analytics
    @GET("api/v1/analytics/get_booking_analytics")
    Call<Map<String, Integer>> getBookingAnalytics();

    @GET("api/v1/analytics/get_user_analytics")
    Call<Map<String, Integer>> getUserAnalytics();

    // Payment
    @POST("api/v1/payment/process_payment")
    Call<Void> processPayment(@Body Map<String, String> paymentDetails);
}