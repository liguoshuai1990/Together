package group

import (
"errors"
"strconv"
"time"
)

var (
	GroupList map[string]*Group
)

func init() {
	GroupList = make(map[string]*Group)
	u := Group{"user_11111", "astaxie", "11111", Profile{"male", 20, "Singapore", "astaxie@gmail.com"}}
	GroupList["user_11111"] = &u
}

type Group struct {
	Id       string
	Username string
	Password string
	Profile  Profile
}

type Profile struct {
	Gender  string
	Age     int
	Address string
	Email   string
}

func AddGroup(g Group) string {
	g.Id = "user_" + strconv.FormatInt(time.Now().UnixNano(), 10)
	GroupList[g.Id] = &g
	return g.Id
}

func GetGroup(gid string) (g *Group, err error) {
	if g, ok := GroupList[gid]; ok {
		return g, nil
	}
	return nil, errors.New("User not exists")
}

func GetAllGroups() map[string]*Group {
	return GroupList
}

func UpdateGroup(gid string, gg *Group) (g *Group, err error) {
	if u, ok := GroupList[gid]; ok {
		if gg.Username != "" {
			u.Username = gg.Username
		}
		if gg.Password != "" {
			u.Password = gg.Password
		}
		if gg.Profile.Age != 0 {
			u.Profile.Age = gg.Profile.Age
		}
		if gg.Profile.Address != "" {
			u.Profile.Address = gg.Profile.Address
		}
		if gg.Profile.Gender != "" {
			u.Profile.Gender = gg.Profile.Gender
		}
		if gg.Profile.Email != "" {
			u.Profile.Email = gg.Profile.Email
		}
		return u, nil
	}
	return nil, errors.New("User Not Exist")
}

func DeleteGroup(gid string) {
	delete(GroupList, gid)
}
