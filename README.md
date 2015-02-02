medication-reminder
===================

## App Wireframes
![a caring reminder v 0 2](https://cloud.githubusercontent.com/assets/2015015/3209467/dd22a5ac-ee70-11e3-9a0a-bf27931da58d.png)

### Screen Flow
![screen shot 2014-06-07 at 11 02 55 am](https://cloud.githubusercontent.com/assets/2015015/3209133/425fca4e-ee5d-11e3-9865-a02bc61c57d5.png)


## Background
My dad, Scott, is a 6’7” commercial fisherman who lives in Washington State and swears a blue streak. 
He has moderate Alzheimer’s and several other medical conditions. He lives with his wife, my mom, 
who takes care of him and organizes his complicated pill boxes as well as reminds him to take his 
medicine. However, he still fishes commercially in Alaska (because he is a badass) and my mom doesn’t 
go with him to Alaska. Mom always sends him with meds in June, but the meds come home in his bag 
untouched at the end of the season in August. 

Dad’s medication regimen is a bit complicated. He has pills he is supposed to take on an empty stomach 
before breakfast, other pills he can take any time during the day, pills he is supposed to take with food 
(usually dinner) and one pill he is only supposed to take when he is already lying down in bed. 
Dad is a little reluctant to take his meds. It’s a pain for him to do it – so sometimes mom has to remind 
him a couple times. When I’m in Alaska on the boat with him he takes them when I bring them to him 
and say “Daddy, please take your pills” but he won’t take them of his own initiative (say when his alarm 
goes off on his phone). When we ask the guys on the fishing boat to remind him it does not seem to 
improve his compliance – either because they don’t remind him or because he doesn’t take the pills 
when they do remind him.

Dad has an iPhone, but he literally only uses it for regular phone calls and he loses it fairly often. 
Anyway, on the fishing boat it is pretty useless, since his motivation for keeping it charged would be nil 
(no coverage for his phone calls in the Bearing Sea), and anyway it will probably live at the bottom of his 
bag for the whole season.

## Problem
Dad needs to take his pills, but he generally won’t do it without a personal reminder from someone he 
really loves and trusts (me or mom). On the fishing boat we can’t be there, and it’s stressful for mom to 
have to keep track of his medications on top of everything else.

## Goal
Get dad to take his pills by using a reminder system that feels as much like being reminded by me or 
mom as possible (who he feels accountable to and wants to stay alive for).

## Solution
The solution is a wearable device that would remind him to take his pills at the appropriate time each 
day by providing a personal message and picture from me or mom asking kindly for him to take his pills 
and reminding him why it matters (capitalizing on his known susceptibility to positive accountability to 
those he loves).

## Story
### My experience setting it up
I would download the app on my phone and on his phone. I would designate an account on his phone
as the primary user (since he’s the one with the watch and the one who will be taking the meds) and the 
account on my phone as a secondary user or observer (since I’m just generating messages, determining 
notification times, and monitoring him – this type of user could also be a physician’s office). His account 
could do everything mine would, but in reality he is unlikely to ever use it.

On my phone, I would generate reminder types (for example for dad they would be: first thing in the 
AM, breakfast, dinner, and bedtime) select photos to be used for the reminders, type messages for 
reminders at each time of day, and type messages for secondary reminders (when he doesn’t respond to 
the first reminder).

### Dad’s Experience
Dad would be going about his day on the fishing boat – perhaps he would be sleeping in his bunk and 
then would get up. When he starts to stir, or when the time reaches a particular hour (say 9am – this is 
a commercial fishing boat – they work at all hours – so this is arbitrary) it is time to take his first set of 
pills.

#### Scenario A: Self report
The phone would buzz and a picture of me with the words “Time to take your AM pills, daddy” 
would appear, with a button at the bottom saying “took the pills”. The picture would stay visible and 
illuminated until he taps “took the pills”.

#### Scenario B: RFID or [alternatives](http://electronics.stackexchange.com/questions/33110/options-for-short-range-distance-determination-between-two-objects) tech distance report
The phone would buzz and a picture of me with the words “Time to take your AM pills, daddy” would 
appear. The picture would stay visible and illuminated until the phone is within 2 feet of his pill box (or 
some specific distance).

If Scott doesn’t take his pills within XX minutes, the phone buzzes again and a reminder message, this 
one slightly firmer appears: “Hey dad! Take those pills!” Now this message would stay visible and
illuminated until the conditions described in A and B above are met.

The same thing would happen for each other allocated pill-taking time in the day, with messages 
customized for the time of day (so that he knows which pills to take) and with pictures being drawn 
from a database so that they change each time and repeat at random.

### Data collection and phone-based dash board access
For each pill-taking time, the presence of an alert, the necessity of a reminder alert, and the time to pill-
taking from first alert would be recorded in a database. The time-series of compliance (which pill-taking 
events were missed, how long to pill-taking from reminder) would be displayed for Scott on his app or 
for me on my app. His phone, obviously, would only transmit data to the server (or to me directly – I 
really don’t know) if dad’s phone had data service. Which is does not in the middle of the Bearing Sea, 
but which it does at his home in the mountains in Washington. The data would have to be stored on his 
phone until data service was available.

## Some stretch features:
When dad has data coverage: Text me and mom if dad hasn’t taken his pills by 1 hours after reminder –
or I can call him or mom can remind him in person
* Bluetooth reporting
* Detection of waking
* Detection of going to bed

# Terms for Contribution
All contributions to this repo will be open source and available to the public.
