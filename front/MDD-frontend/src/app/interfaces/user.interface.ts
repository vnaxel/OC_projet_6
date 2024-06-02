/* 
{
    "email": "email@123.com",
    "username": "debool",
    "interestedTopics": null,
    "created_at": "2024-06-01T10:06:21.908387",
    "updated_at": "2024-06-01T10:06:21.908408"
}
*/

export interface User {
    email: string;
    username: string;
    interestedTopics: string[];
    created_at: string;
    updated_at: string;
}