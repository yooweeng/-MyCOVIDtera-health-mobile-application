# MyCOVIDtera
## Purpose
MyCOVIDtera aims to support the country’s progress in combating the spread of COVID-19 by developing a solution to COVID-19 close contact problem. The contact tracing problem refers to the process of identifying, assessing, and managing people who have been exposed to someone else that has been infected with the virus. Therefore, by implementing our application in daily lives, we can track the source of clusters for COVID-19 infections through a check-in and check-out system on premises, and assign a risk status to the users who may have been exposed to the victims of COVID-19. By assigning a risk status to all the users of MyCOVIDtera, the users that have been identified with high-risk status will take extra precautions to reduce the spread in case they have been exposed by a positively identified victim. Thereby achieving the interruption of widely spread virus to the communities in Malaysia. The application also serves as a health
monitoring system for the users, as they can update their health assessments regularly to make sure
they are healthy.

To sum up, the application is intended for all demographic age groups and genders. Our
UI design goal is to keep it as simplistic as possible so that even the elders would be able to learn
to use our application easily. Furthermore, we had chosen a vibrant purple color as the theme of
our design to make the overall UI pleasing to the eyes for both genders.

## Key Features
## User Profile, Edit User Profile, and Logout
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/7ecf71b0-8b18-457e-93d6-03e4e3b04a47)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/9ece8d88-fc1a-4661-8677-1c91d4de3142)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/cce434bb-f895-4b90-afde-d12fbd87a4fd)

The Profile fragment contains user personal information such as name, phone number, NRIC, and
state. Besides that, user can also check for their current Risk Status and their COVID-19
vaccination certificates. On this fragment, user can click on the toolbox to edit their personal
information as shown in the second figure. We only allow users to change their phone number,
password, gender and state.

## Access to functionalities provided and view latest COVID-19 related news
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/38c2a3bf-1e5e-4a66-bb50-cd66ed23804d)

In home fragment, user can access to those functionalities provided by clicking on the service card buttons, such as
Risk Status, Vaccination, Self Report, Hotspot, Hotline, SOP Guideline, SOP Violations, FAQs. Besides, user can also click on the ‘things to do’ slider to view the SOP guidelines.

![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/e1076273-5f7b-4a6b-b099-ee33346d0e92)

When scrolling down the fragment, user can have a brief view on the latest news, and if user wants
to have a deep look at the news, the user can click on the ‘read more’ button, then the news will
be displayed in the web view. Also, user can like news or have a look at the total number of views
for each news.

## Update the Risk Status of User through Health Assessment and Self-Report
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/ef65b037-1b49-436a-b2f8-08dd50147261)

There are two ways to update the risk status of the user. First is through the Health Risk Assessment
fragment which contains 4 questions. The purpose of this fragment is to evaluate user’s current
health status.

![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/8d1df9a2-af6b-48e6-9759-9a50169aac77)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/afe786a8-5de1-4d3f-a635-956d2183874a)

The second method to update the risk status is through the Self Report fragment, where users that
had tested COVID-19 test kits may update their results, and a risk status will be assigned to the
user. Besides, toast messages will be shown when the user clicks on the submit button.

## Booking the COVID-19 Vaccination and Viewing the Vaccination Status/ Certificates
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/ee14249a-7767-4c7d-a46b-77a2ee481a4a)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/b015a21a-48ad-4b6a-9717-c1ba3fad76a8)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/41dfb848-fb12-4a80-9836-1e0fba903487)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/f13bcb94-135b-432f-b0ab-039ca9614dc1)

In vaccination fragment, users can make vaccination appointment by clicking the ‘Appoint
Vaccine’ button. In MyCOVIDtera, users must complete the previous vaccinations before
appointing for the next vaccinations. After clicking on the button, users
will be redirected to the appointment fragment to complete the remaining process.

## QR Check-in, History, and Check-out
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/b21f62c5-9cda-4e42-92d0-f3f2eb3e475e)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/49c12019-356f-4fb5-a856-7c3cf2959b40)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/4c3f431c-1bdb-44dd-8c1a-36b128c0b420)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/e47a2190-ef37-4b2e-96f3-db41e41ac23e)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/5e1bbd77-aff6-4d4b-babe-afcd6e48fc5c)

Users can then go to the Check-in History fragment through the button on the Trace fragment to
view their check-in histories. They can also press on the check-out button to record the time they
left the premise.

## COVID-19 Hotspot
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/ee7a6056-5053-440b-b3bf-abbf4cc4bf46)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/632d494f-fe27-4430-a0b8-30833d21a58d)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/e77bd1f2-dcf8-4b9d-8140-57df25cba8f1)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/99dd7873-3338-4563-a555-19aa36288457)

he Hotspot fragment informs users of the number of COVID-19 positive users near the premise.
For example, from the left figure to the right, the first display that there are currently no reported
COVID-19 cases within a 500m radius from the user. However, if a user that has a risk status that
has been identified with positive COVID-19 like the second figure checks in into a premise with
the QR scanner in third figure, then it will result in the last figure. Where the application will inform
the user of the number of cases that have been reported within 500m of the user. The map and red
zone feature are implemented through Google Maps API, while the counter for cases is from
Firebase based on the distance of the user to the check-in point.

## Report SOP Violation
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/81196a03-dfb7-4819-a351-b60bc4b0cc06)

The SOP Violation fragment allows users to send in reports about people who have violated the
SOP for COVID-19. The title, date, time, description and evidence are all required and will be
validated. The evidences section is to make users select an image as part of their evidence that the
violation had occurred. If everything is filled and correct, pressing on the submit button will
display a toast message on the status of the submission.

## COVID-19 Statistics
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/aef633ba-5d6b-4002-adfd-1d4e3c7992bf)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/836ada29-189b-4049-a583-130accf22716)
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/a3ef8298-19c9-400d-a052-6c5339776474)

The Statistics fragment shows the user the daily statistics of COVID-19 cases in Malaysia. We
have retrieved the information using retrofit from two rest api providers, namely NovelCOVID API (disease.sh) and 
Coronavirus COVID19 API (covid19api.com) as both provide real-time covid related data.

The first figure show the number of daily
cases alongside a visual graphic for the past weeks. For example, assuming today is Monday, and
hence the bar is highlighted in purple, while the Tuesday bar contains the number of COVID-19
cases from 6 days ago. Hence, we get to visualize the trends for the past week, and the bar is
implemented by modifying and customizing the ProgressBar view. We have also calculated the
percentage of change between today and yesterday, and in the figure above, we can see that there
is a 25% decrease of COVID-19 cases compared to Sunday. Furthermore, we have also shown the
total number of recovered cases for the day and the total recovery rate in Malaysia. The second
part of our Statistics fragment is the graph for the total COVID-19 cases in Malaysia shown in the
second figure. We have used a custom MarkerView so that when the user clicks on a data on the
graph, it will display the total number of cases for that day alongside the date. The user can also
zoom and scroll the graph, and for aesthetic purposes, we remove the x and y axes. The third part
as shown in the third figure shows two CardViews, which are the case per people and total deaths
in Malaysia, with a daily death counter.

## Use case diagram
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/f1b0d115-1644-48f7-bedb-23b2e3ffed62)

## Architecture diagram
![image](https://github.com/yooweeng/MyCOVIDtera-health-mobile-application/assets/96167642/74322629-606d-42aa-bb11-68b1165880b8)

### Official documentation of rest api providers used in the project
- https://disease.sh/docs/
- https://api.covid19api.com/docs/
