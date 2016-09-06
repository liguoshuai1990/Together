package group

import (
	"github.com/nu7hatch/gouuid"
	"errors"
)

var (
	GroupList map[string]*Group
)

func init() {
	GroupList = make(map[string]*Group)
	g := Group{"group_11111", "hello", []string{"11111"}}
	GroupList["user_11111"] = &g
}

type Group struct {
	Id       string
	Topic    string
	Users    []string
}

func AddGroup(g Group) string {
	gid, _ := uuid.NewV4()
	g.Id = gid.String()
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
	if g, ok := GroupList[gid]; ok {
		if gg.Users != "" {
			g.Users = gg.Users
		}
		return g, nil
	}
	return nil, errors.New("User Not Exist")
}

func DeleteGroup(gid string) {
	delete(GroupList, gid)
}
